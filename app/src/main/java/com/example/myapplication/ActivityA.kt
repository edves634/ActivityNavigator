package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {
    // Ленивая инициализация SharedPreferences для хранения данных
    private val sharedPrefs by lazy {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
    }

    // Регистрация обработчика результата от ActivityB
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Обработка результата при возврате из ActivityB
        if (result.resultCode == RESULT_OK) {
            // Извлечение данных, переданных через Intent
            val data = result.data?.getStringExtra("RESULT_FROM_BC") ?: ""

            // Показ Toast с полученными данными
            Toast.makeText(this, "Получено из B и C: $data", LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a) // Установка layout

        // Обработчик клика по кнопке перехода к ActivityB
        findViewById<Button>(R.id.btn_to_b).setOnClickListener {
            // Сохранение данных в SharedPreferences перед переходом
            sharedPrefs.edit().putString("FROM_A", "screen A").apply()

            // Запуск ActivityB с ожиданием результата
            resultLauncher.launch(Intent(this, ActivityB::class.java))
        }
    }
}