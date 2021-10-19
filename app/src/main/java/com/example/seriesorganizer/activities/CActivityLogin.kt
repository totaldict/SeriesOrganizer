package com.example.seriesorganizer.activities

import android.content.Context
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

        // Проверяем, есть ли запись о пользователе. Если есть - переходим на другую форму.
        val readSharedPref = applicationContext.getSharedPreferences(getString(R.string.FILE_NAME_PREFERENCES), Context.MODE_PRIVATE)
        val sharedLogin = readSharedPref.getString(getString(R.string.PARAM_LOGIN), "")
        //TODO Надо как-то шифровать пасс, илии не тут хранить
        val sharedPassword = readSharedPref.getString(getString(R.string.PARAM_PASSWORD), "")
        if (accessCheck(sharedLogin, sharedPassword)) {
            val intent1 = Intent(this, CActivityMainList::class.java)
            startActivity(intent1)
            return
        }


        binding.btLogin.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            if (!accessCheck(login, password)) {
                return@setOnClickListener
            }

            // устанавливаем логин/пасс в файл настроек
            val sharedPref = applicationContext.getSharedPreferences(getString(R.string.FILE_NAME_PREFERENCES), Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString(getString(R.string.PARAM_LOGIN), login)
                putString(getString(R.string.PARAM_PASSWORD), password)
                apply()
            }

            val intent = Intent(this, CActivityMainList::class.java)
            startActivity(intent)
        }
    }

    /** Функция проверки логина/пароля */
    private fun accessCheck(login: String?, password: String?): Boolean {
        //TODO сюда добавить запрос, пускает ли по этому лгину/пассу
        return !login.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}