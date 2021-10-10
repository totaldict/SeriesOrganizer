package com.example.seriesorganizer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.seriesorganizer.R
import com.example.seriesorganizer.databinding.ActivityMainListBinding
import com.example.seriesorganizer.databinding.ActivityTvSeriesBinding

class CActivityTvSeries : AppCompatActivity() {
    private lateinit var binding: ActivityTvSeriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvSeriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}