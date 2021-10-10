package com.example.seriesorganizer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.seriesorganizer.R
import com.example.seriesorganizer.databinding.ActivityLoginBinding

class CActivityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btLogin.setOnClickListener{
            val intent = Intent(this, CActivityMainList::class.java)
            intent.putExtra("LOGIN", binding.etLogin.text.toString())
            startActivity(intent)
        }
    }
}