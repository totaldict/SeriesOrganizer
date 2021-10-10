package com.example.seriesorganizer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriesorganizer.R
import com.example.seriesorganizer.adapters.CRecyclerViewTvSeriesListAdapter
import com.example.seriesorganizer.databinding.ActivityMainListBinding
import com.example.seriesorganizer.model.CTvSeries

/** Основной лист со списком сериалов */
class CActivityMainList : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding
    private val tvSeriesList = ArrayList<CTvSeries>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Заглушка
        tvSeriesList.add(CTvSeries(123, "The Mandalorian", "Одинокий мандалорец-наёмник живёт на краю обитаемой галактики, куда не дотягивается закон Новой Республики. Представитель некогда могучей расы благородных воинов теперь вынужден влачить жалкое существование среди отбросов общества.", "Эпизод 1 Chapter 1: The Mandalorian 12 ноября 2019 Эпизод 2 Chapter 2: The Child 15 ноября 2019 Эпизод 3 Chapter 3: The Sin 22 ноября 2019"))
        tvSeriesList.add(CTvSeries(456, "True Detective", "Первый сезон. В Луизиане в 1995 году происходит странное убийство девушки. В 2012 году дело об убийстве 1995 г. повторно открывают, так как произошло похожее убийство. Дабы лучше продвинуться в расследовании, полиция решает допросить бывших детективов, которые работали над делом в 1995 г.", "Сезон 1 2015, эпизодов: 8 Эпизод 1 Долгая яркая тьма The Long Bright Dark 9 января 2015 Эпизод 2 Галлюцинации Seeing Things 9 января 2015 Эпизод 3 Замкнутая комната The Locked Room 16 января 2015 Эпизод 4 Стой, кто идет Who Goes There 16 января 2015"))
        tvSeriesList.add(CTvSeries(789, "Miracle Workers", "Небесная канцелярия Бога — масштабный офис с множеством отделов и сотрудников, работающих в атмосфере цейтнота и многозадачности. Есть даже HR и напоминающий службу технической поддержки отдел обработки молитв. Именно его служащие, ангелы Элиза и Крэйг, берутся за спасение Земли, когда приунывший Господь вдруг решает её погубить.", ""))
        tvSeriesList.add(CTvSeries(55, "Why Women Kill", "", "Эпизод 1 Murder Means Never Having to Say You're Sorry 15 августа 2019 Эпизод 2 I'd Like to Kill Ya, But I Just Washed My Hair 22 августа 2019 Эпизод 3 I Killed Everyone He Did, But Backwards and in High Heels 29 августа 2019"))

        val listener = CRecyclerViewTvSeriesListAdapter.IClickListener { tvSeries, index ->
            Toast.makeText(this@CActivityMainList, tvSeries.name, Toast.LENGTH_SHORT).show()

//            val intent = Intent(this@CActivityMainList, CActivityTvSeries::class.java)
//            intent.putExtra("PARAM_TVSERIES_ID", tvSeries.id)
//            intent.putExtra("PARAM_TVSERIES_NAME", tvSeries.name)
//            intent.putExtra("PARAM_TVSERIES_INFO", tvSeries.info)
//            intent.putExtra("PARAM_TVSERIES_EPISODES", tvSeries.episodes)
//            resultLauncher.launch(intent)
        }

        val adapter = CRecyclerViewTvSeriesListAdapter(tvSeriesList, listener)
        binding.rvTvSeriesList.adapter = adapter

        binding.rvTvSeriesList.layoutManager = LinearLayoutManager(this) // выводит в виде списка




    }
}