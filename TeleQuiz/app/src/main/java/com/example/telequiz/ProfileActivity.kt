package com.example.telequiz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Perfil y Estadísticas"

        val prefs = getSharedPreferences("TeleQuizPrefs", MODE_PRIVATE)

        val playerName = prefs.getString("playerName", "N/A")
        val startTime = prefs.getString("startTime", "N/A")
        val gamesPlayed = prefs.getInt("gamesPlayed", 0)

        findViewById<TextView>(R.id.playerNameTextView).text = "Jugador: $playerName"
        findViewById<TextView>(R.id.startTimeTextView).text = "Inicio: $startTime"
        findViewById<TextView>(R.id.gamesPlayedTextView).text = "Cantidad de Partidas: $gamesPlayed"

        val gson = Gson()
        val historyJson = prefs.getString("gameHistory", "[]")
        val type = object : TypeToken<MutableList<Partida>>() {}.type
        val gameHistory: MutableList<Partida> = gson.fromJson(historyJson, type)

        val recyclerView = findViewById<RecyclerView>(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistorialAdapter(gameHistory.reversed())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
