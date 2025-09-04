package com.example.telequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val nombre = nameEditText.text.toString()
            if (nombre.isNotBlank()) {

                val prefs = getSharedPreferences("TeleQuizPrefs", MODE_PRIVATE)

                if (!prefs.contains("playerName")) {
                    val editor = prefs.edit()
                    editor.putString("playerName", nombre)
                    val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm'hs'", Locale.getDefault())
                    editor.putString("startTime", sdf.format(Date()))
                    editor.apply()
                }

                val intent = Intent(this, TopicSelectionActivity::class.java)
                intent.putExtra("NOMBRE_USUARIO", nombre)
                startActivity(intent)

            } else {
                nameEditText.error = "Por favor, ingresa tu nombre"
            }
        }
    }
}
