package com.example.seriesorganizer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.seriesorganizer.databinding.ActivityAddBinding
import com.example.seriesorganizer.utils.rest.CApiResponseSearch
import com.example.seriesorganizer.utils.rest.CRetrofitBuilder
import com.example.seriesorganizer.utils.rest.IServerAPITemplate
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import com.example.seriesorganizer.model.EFilmType
import android.view.ViewGroup

import android.R
import android.util.Log
import android.view.View
import com.example.seriesorganizer.dao.IDAOTvSeries
import com.example.seriesorganizer.model.CTvSeries
import com.example.seriesorganizer.utils.CDatabase
import com.example.seriesorganizer.utils.rest.CApiResponseEpisodes
import java.time.LocalDate


class CActivityAdd : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var searchResult: CApiResponseSearch
    private lateinit var episodes: CApiResponseEpisodes
    private val foundSerials = ArrayList<String>()
    private var selectedPosition = -1
    private var selectedId = 0
    private lateinit var daoTvSeries: IDAOTvSeries
    private lateinit var newTvSeries: CTvSeries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retrofit = CRetrofitBuilder.getRetrofit()
        val service = retrofit.create(IServerAPITemplate::class.java)
        newTvSeries = CTvSeries(0, "", "", ArrayList())
        val db = CDatabase.getDatabase(this)
        daoTvSeries = db.daoTvSeries()

        binding.btnSearch.setOnClickListener {
            val listView: ListView = binding.lvSearchResult
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            foundSerials.clear()

            lifecycleScope.launch {
                val searchName = binding.etSeriesToSearch.text.toString()
                // Запрос на сервер с поиском
                searchResult = service.getSearchByKeyword(searchName, 1)
                if (searchResult.films.isNullOrEmpty()) {
                    Toast.makeText(this@CActivityAdd, "По запросу $searchName ничего не найдено.", Toast.LENGTH_LONG).show()
                    return@launch
                }
                // формируем записи в список
                searchResult.films.forEach {
                    val name = it.nameRu ?: it.nameEn ?: "Название отсутствует"
                    val type = if ( it.type == EFilmType.TvSeries.value)"(сериал)" else ""
                    val year = if (!it.year.isNullOrEmpty()) "${it.year}" else  ""
                    val rating = if (!it.rating.isNullOrEmpty() && it.rating !== "null") "${it.rating}" else ""
                    val resultStr = "$name $type $year $rating"
                    foundSerials.add(resultStr)
                }
                // выводим список
//                listView.adapter = ArrayAdapter(this@CActivityAdd, android.R.layout.simple_list_item_1, foundSerials)

                listView.setAdapter(object : ArrayAdapter<Any?>(this@CActivityAdd, android.R.layout.simple_list_item_1,
                    foundSerials as List<Any?>
                ) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val renderer: View = super.getView(position, convertView, parent!!)
                        if (position == selectedPosition) {
                            //TODO: set the proper selection color here:
                            renderer.setBackgroundResource(android.R.color.darker_gray)
                        } else {
                            renderer.setBackgroundResource(android.R.color.transparent)
                        }
                        return renderer
                    }
                })
            }

            listView.setOnItemClickListener { _, _, position, _ ->
                selectedPosition = position
                selectedId = searchResult.films[position].filmId
                listView.setSelection(position)
                newTvSeries.id = selectedId
                newTvSeries.name = searchResult.films[position].nameRu ?: searchResult.films[position].nameRu ?: "Название отсутствует"
                newTvSeries.description = searchResult.films[position].description ?: ""
            }

            // Кнопка добавления сериала
            binding.btnAddNewTvSeries.setOnClickListener {
                if (selectedId == 0) {
                    Toast.makeText(this@CActivityAdd, "Ничего не выбрано.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                // 1. запрашиваем инфо по сериалу
                lifecycleScope.launch {
                    episodes = service.getSeasons(selectedId)
                    if (episodes.items.isNullOrEmpty()) {
                        Toast.makeText(this@CActivityAdd, "Либо это не сериал, либо отсутствуют серии. Пока не могу добавить.", Toast.LENGTH_LONG).show()
                        return@launch
                    }
                    val episodesDates = ArrayList<LocalDate>()
                    episodes.items!!.forEach { season ->
                        season.episodes?.forEach { episode ->
                            val date = LocalDate.parse(episode.releaseDate)
                            episodesDates.add(date)
                        }
                    }
                    newTvSeries.episodes = episodesDates
                }
                //TODO По ходу два lifecycleScope запускаются одновременно.

                lifecycleScope.launch {
                    // 2. Добавляем в БД
                    val tvSeriesFromDB = daoTvSeries.findById(selectedId)
                    tvSeriesFromDB?.let {
                        daoTvSeries.update(newTvSeries)
                    } ?: run {
                        daoTvSeries.insert(newTvSeries)
                    }
                }
                finish()
            }




        }
    }
}