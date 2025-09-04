package com.example.telequiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuizActivity : AppCompatActivity() {

    private lateinit var preguntas: List<Pregunta>
    private var indicePreguntaActual = 0
    private var puntaje = 0
    private var rachaCorrectas = 0
    private var rachaIncorrectas = 0

    private val respuestasUsuario = mutableMapOf<Int, Int>()

    private lateinit var topicTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var optionButtons: List<Button>
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    private var quizStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "TeleQuiz"

        topicTextView = findViewById(R.id.topicTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        questionTextView = findViewById(R.id.questionTextView)
        optionButtons = listOf(
            findViewById(R.id.option1Button),
            findViewById(R.id.option2Button),
            findViewById(R.id.option3Button),
            findViewById(R.id.option4Button)
        )
        prevButton = findViewById(R.id.prevButton)
        nextButton = findViewById(R.id.nextButton)

        val tema = intent.getStringExtra("TEMA") ?: "Desconocido"
        topicTextView.text = tema
        preguntas = BancoDePreguntas.obtenerPreguntas(tema).shuffled().take(7)

        quizStartTime = System.currentTimeMillis()
        registrarNuevaPartida(tema)

        mostrarPregunta()

        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener { manejarRespuesta(index) }
        }

        nextButton.setOnClickListener {
            if (indicePreguntaActual < preguntas.size - 1) {
                indicePreguntaActual++
                mostrarPregunta()
            } else {
                val quizEndTime = System.currentTimeMillis()
                val duracion = (quizEndTime - quizStartTime) / 1000
                actualizarPartida("Terminada", puntaje, duracion)

                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("PUNTAJE_FINAL", puntaje)
                startActivity(intent)
                finish()
            }
        }

        prevButton.setOnClickListener {
            if (indicePreguntaActual > 0) {
                indicePreguntaActual--
                mostrarPregunta()
            }
        }
    }

    private fun registrarNuevaPartida(tema: String) {
        val prefs = getSharedPreferences("TeleQuizPrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        val gamesPlayed = prefs.getInt("gamesPlayed", 0) + 1
        editor.putInt("gamesPlayed", gamesPlayed)

        val historyJson = prefs.getString("gameHistory", "[]")
        val type = object : TypeToken<MutableList<Partida>>() {}.type
        val gameHistory: MutableList<Partida> = gson.fromJson(historyJson, type)

        val nuevaPartida = Partida(gamesPlayed, tema, 0, 0, "En curso")
        gameHistory.add(nuevaPartida)

        editor.putString("gameHistory", gson.toJson(gameHistory))
        editor.apply()
    }

    private fun actualizarPartida(estado: String, puntajeFinal: Int, duracion: Long) {
        val prefs = getSharedPreferences("TeleQuizPrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()

        val historyJson = prefs.getString("gameHistory", "[]")
        val type = object : TypeToken<MutableList<Partida>>() {}.type
        val gameHistory: MutableList<Partida> = gson.fromJson(historyJson, type)

        if (gameHistory.isNotEmpty()) {
            val partidaActual = gameHistory.last()
            partidaActual.estado = estado
            partidaActual.puntaje = puntajeFinal
            partidaActual.tiempo = duracion
        }

        editor.putString("gameHistory", gson.toJson(gameHistory))
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                actualizarPartida("Canceló", puntaje, 0)
                finish()
                return true
            }
            R.id.action_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mostrarPregunta() {
        if (respuestasUsuario.containsKey(indicePreguntaActual)) {
            mostrarPreguntaRespondida()
        } else {
            resetearEstadoBotones()
            val pregunta = preguntas[indicePreguntaActual]
            questionTextView.text = "${indicePreguntaActual + 1}. ${pregunta.texto}"
            pregunta.opciones.forEachIndexed { index, opcion ->
                optionButtons[index].text = opcion
            }
            nextButton.isEnabled = false
        }

        prevButton.isEnabled = indicePreguntaActual > 0 && respuestasUsuario.containsKey(indicePreguntaActual - 1)
    }

    private fun manejarRespuesta(indiceOpcionSeleccionada: Int) {
        respuestasUsuario[indicePreguntaActual] = indiceOpcionSeleccionada
        val pregunta = preguntas[indicePreguntaActual]
        if (indiceOpcionSeleccionada == pregunta.indiceRespuestaCorrecta) {
            rachaCorrectas++
            rachaIncorrectas = 0
            val puntosGanados = 2 * (1 shl (rachaCorrectas - 1))
            puntaje += puntosGanados
            Toast.makeText(this, "¡Correcto! +$puntosGanados", Toast.LENGTH_SHORT).show()
        } else {
            rachaIncorrectas++
            rachaCorrectas = 0
            val puntosPerdidos = 3 * (1 shl (rachaIncorrectas - 1))
            puntaje -= puntosPerdidos
            Toast.makeText(this, "Incorrecto. -$puntosPerdidos", Toast.LENGTH_SHORT).show()
        }
        scoreTextView.text = puntaje.toString()
        mostrarPreguntaRespondida()
    }

    private fun mostrarPreguntaRespondida() {
        val pregunta = preguntas[indicePreguntaActual]
        val indiceRespuestaUsuario = respuestasUsuario[indicePreguntaActual]!!
        val indiceCorrecto = pregunta.indiceRespuestaCorrecta

        questionTextView.text = "${indicePreguntaActual + 1}. ${pregunta.texto}"
        pregunta.opciones.forEachIndexed { index, opcion ->
            optionButtons[index].text = opcion
        }

        optionButtons.forEach { it.isEnabled = false }
        optionButtons[indiceCorrecto].setBackgroundColor(Color.GREEN)
        if (indiceRespuestaUsuario != indiceCorrecto) {
            optionButtons[indiceRespuestaUsuario].setBackgroundColor(Color.RED)
        }

        nextButton.isEnabled = true
    }

    private fun resetearEstadoBotones() {
        optionButtons.forEach { button ->
            button.setBackgroundColor(Color.parseColor("#FF6200EE"))
            button.isEnabled = true
        }
    }
}
