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
import com.shuhart.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class CActivityTvSeries : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesBinding

    private lateinit var tvSeries: CTvSeries
    private lateinit var daoTvSeries: IDAOTvSeries
    private val dateEpisodes = ArrayList<CalendarDay>()

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

            tvSeries.episodes.forEach { date ->
                val newDate = CalendarDay.from(date.year, date.monthValue - 1, date.dayOfMonth)
                dateEpisodes.add(newDate)
            }
            binding.calendarEpisodes.dispatchSetSelected(false)
            dateEpisodes.forEach { date ->
                binding.calendarEpisodes.setDateSelected(date, true)

            }


        }


    }



}