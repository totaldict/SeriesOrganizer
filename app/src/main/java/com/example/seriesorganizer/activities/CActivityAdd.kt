package com.example.seriesorganizer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.seriesorganizer.R
import com.example.seriesorganizer.databinding.ActivityAddBinding
import com.example.seriesorganizer.databinding.ActivityTvSeriesBinding
import com.example.seriesorganizer.utils.rest.CRetrofitBuilder
import com.example.seriesorganizer.utils.rest.IServerAPITemplate
import kotlinx.coroutines.launch

class CActivityAdd : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retrofit = CRetrofitBuilder.getRetrofit()
        val service = retrofit.create(IServerAPITemplate::class.java)

        binding.btnSearch.setOnClickListener {
            var searchResult: String
            lifecycleScope.launch {
                val searchName = binding.etSeriesToSearch.text.toString()
                searchResult = service.getSearchByKeyword(searchName, 1)
                Log.d("LOG:::::::::::", searchResult)
                Toast.makeText(this@CActivityAdd, searchResult, Toast.LENGTH_LONG).show()
            }

        }
    }
}