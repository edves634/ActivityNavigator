package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c) // Установка layout

        // Получение данных из ActivityB через Intent
        val dataFromB = intent.getStringExtra("FROM_B") ?: "" // Извлечение данных

        // Отображение полученных данных в Toast
        Toast.makeText(this, "Данные из B: $dataFromB", LENGTH_SHORT).show()

        // Обработчик клика по кнопке возврата
        findViewById<Button>(R.id.btn_back).setOnClickListener {
            // Создание Intent для возврата результата
            Intent().apply {
                // Упаковка возвращаемых данных
                putExtra("RESULT_FROM_C", "screen C")

                // Установка результата как RESULT_OK с данными
                setResult(RESULT_OK, this)
            }
            // Завершение ActivityC и возврат в ActivityB
            finish()
        }
    }
}