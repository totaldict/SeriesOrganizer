package com.example.seriesorganizer.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriesorganizer.R
import com.example.seriesorganizer.adapters.CRecyclerViewTvSeriesListAdapter
import com.example.seriesorganizer.dao.IDAOTvSeries
import com.example.seriesorganizer.databinding.ActivityMainListBinding
import com.example.seriesorganizer.model.CTvSeries
import com.example.seriesorganizer.utils.CDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/** Основной лист со списком сериалов */
class CActivityMainList : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding
    private val tvSeriesList = ArrayList<CTvSeries>()
    var arrayDate = ArrayList<LocalDate>()

    private lateinit var daoTvSeries: IDAOTvSeries

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val activity = data?.getStringExtra("PARAM_ACTIVITY_NAME")
            when (activity) {
                "CActivityTvSeries" -> {
                    // тут можно зацепить логику после закрытия окна CActivityTvSeries
                    // val x = data?.getIntExtra("PARAM_123", 0)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        TODO:
//        1. Добавить использование БД.
//        2. Добавить форму добавления/редактирования записи.


        val listener = object : CRecyclerViewTvSeriesListAdapter.IClickListener {
            override fun onItemClick(tvSeries: CTvSeries, index: Int) {
                val intent = Intent(this@CActivityMainList, CActivityTvSeries::class.java)
                intent.putExtra(getString(R.string.PARAM_SERIES_ID), tvSeries.id)
                resultLauncher.launch(intent)
            }
        }

        val adapter = CRecyclerViewTvSeriesListAdapter(tvSeriesList, listener)
        binding.rvTvSeriesList.adapter = adapter

        binding.rvTvSeriesList.layoutManager = LinearLayoutManager(this) // выводит в виде списка

        // получаем БД
        val db = CDatabase.getDatabase(this)
        daoTvSeries = db.daoTvSeries()
        lifecycleScope.launch {
            createInitialData(daoTvSeries)
        }
        // специальный объект, отвечает за отслеживание жизни текущей активности приложения
        lifecycleScope.launch {
            daoTvSeries.getAllFlow().collect {
                // Update the UI.
                adapter.updateData(it)
            }
        }

        // нижнее меню
        binding.bottomNavigationLessonList.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.miCalendar -> {
                    Toast.makeText(this@CActivityMainList, "Открываем общий календарь", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.miAddTvSeries -> {
//                    val tvSeries = CTvSeries(1, "", "", ArrayList<LocalDate>())
                    lifecycleScope.launch {
//                        Toast.makeText(this@CActivityMainList, "Открываем форму добавления сериала", Toast.LENGTH_SHORT).show()
//                        daoTvSeries.insert(tvSeries)                              // TODO Если не буду редактирование делать, то можно убрать этот кусок
                        val intent = Intent(this@CActivityMainList, CActivityAdd::class.java)
//                        intent.putExtra(getString(R.string.PARAM_SERIES_ID), tvSeries.id.toString())
                        resultLauncher.launch(intent)
                    }


                    true
                }
                else -> false
            }
        }


    }

    // Функция инициализации для теста. будет выполняться в фоновом потоке
    private suspend fun createInitialData(daoTvSeries : IDAOTvSeries) = withContext(Dispatchers.IO) // говорит в каком потомке будет выполняться
    {
        // если не пустая БД - то не выставляем
        if (daoTvSeries.getAll().isNotEmpty())
            return@withContext
//        arrayDate.add(LocalDate.parse("2011-04-14") )
//        arrayDate.add(LocalDate.parse("2011-05-01"))
//        // Заглушка
//        tvSeriesList.add(CTvSeries(123, "The Mandalorian", "Одинокий мандалорец-наёмник живёт на краю обитаемой галактики, куда не дотягивается закон Новой Республики. Представитель некогда могучей расы благородных воинов теперь вынужден влачить жалкое существование среди отбросов общества.", arrayDate))
//        tvSeriesList.add(CTvSeries(456, "True Detective", "Первый сезон. В Луизиане в 1995 году происходит странное убийство девушки. В 2012 году дело об убийстве 1995 г. повторно открывают, так как произошло похожее убийство. Дабы лучше продвинуться в расследовании, полиция решает допросить бывших детективов, которые работали над делом в 1995 г.", arrayDate))
//        tvSeriesList.add(CTvSeries(789, "Miracle Workers", "Небесная канцелярия Бога — масштабный офис с множеством отделов и сотрудников, работающих в атмосфере цейтнота и многозадачности. Есть даже HR и напоминающий службу технической поддержки отдел обработки молитв. Именно его служащие, ангелы Элиза и Крэйг, берутся за спасение Земли, когда приунывший Господь вдруг решает её погубить.", arrayDate))
//        tvSeriesList.add(CTvSeries(55, "Why Women Kill", "", arrayDate))
//
//        tvSeriesList.forEach { it ->
//            daoTvSeries.insert(it)
//        }
    }

}