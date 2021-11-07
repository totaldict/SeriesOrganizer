package com.example.seriesorganizer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.seriesorganizer.R
import com.example.seriesorganizer.dao.IDAOTvSeries
import com.example.seriesorganizer.databinding.ActivityMainListBinding
import com.example.seriesorganizer.databinding.ActivityTvSeriesBinding
import com.example.seriesorganizer.model.CTvSeries
import com.example.seriesorganizer.utils.CDatabase
import kotlinx.coroutines.launch
import java.time.LocalDate

class CActivityTvSeries : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesBinding

    private lateinit var tvSeries: CTvSeries
    private lateinit var daoTvSeries: IDAOTvSeries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db = CDatabase.getDatabase(this)
        daoTvSeries = db.daoTvSeries()
        val id = intent.getIntExtra(getString(R.string.PARAM_SERIES_ID), -1)
        lifecycleScope.launch {
            tvSeries = daoTvSeries.findById(id)?: CTvSeries(-1, "", "", ArrayList<LocalDate>()) // TODO Добавить сюда проверку, если ничего не нашел - закрывать форму и передавать на предыдущую статусс закрытия
            binding.tvName.setText(tvSeries?.name)
            binding.tvDescription.setText(tvSeries?.description)

            // TODO Заглушка, списко серий в текстовом виде. Заменить на календарь
            val strEpisodes = tvSeries.episodes.joinToString(System.lineSeparator())
            binding.tvEpisodes.setText(strEpisodes)

        }


    }



}