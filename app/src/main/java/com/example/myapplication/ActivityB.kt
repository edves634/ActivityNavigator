package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ActivityB : AppCompatActivity() {
    // Ленивая инициализация SharedPreferences (общее хранилище с ActivityA)
    private val sharedPrefs by lazy {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
    }

    // Регистрация обработчика результата от ActivityC
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Обработка результата при возврате из ActivityC
        if (result.resultCode == RESULT_OK) {
            // Извлечение данных из ActivityC
            val resultFromC = result.data?.getStringExtra("RESULT_FROM_C") ?: ""

            // Создание Intent для передачи результата в ActivityA
            val resultIntent = Intent().apply {
                putExtra("RESULT_FROM_BC", resultFromC) // Упаковка данных
            }

            // Установка результата для ActivityA
            setResult(RESULT_OK, resultIntent)

            // Завершение ActivityB после установки результата
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b) // Установка layout

        // Получение данных из ActivityA через SharedPreferences
        val dataFromA = sharedPrefs.getString("FROM_A", "") ?: ""

        // Отображение полученных данных в Toast
        Toast.makeText(this, "Данные из A: $dataFromA", LENGTH_SHORT).show()

        // Обработчик клика по кнопке перехода к ActivityC
        findViewById<Button>(R.id.btn_to_c).setOnClickListener {
            // Создание Intent для запуска ActivityC
            Intent(this, ActivityC::class.java).apply {
                // Передача данных в ActivityC через Intent
                putExtra("FROM_B", "screen B")

                // Запуск ActivityC с ожиданием результата
                resultLauncher.launch(this)
            }
        }
    }
}