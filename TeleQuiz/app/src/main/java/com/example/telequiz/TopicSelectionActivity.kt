package com.example.telequiz

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class TopicSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_selection)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "TeleQuiz"

        val welcomeTextView: TextView = findViewById(R.id.welcomeTextView)
        val redesButton: Button = findViewById(R.id.redesButton)
        val ciberseguridadButton: Button = findViewById(R.id.ciberseguridadButton)
        val microondasButton: Button = findViewById(R.id.microondasButton)

        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
        welcomeTextView.text = "BIENVENIDO\n$nombreUsuario"

        redesButton.setOnClickListener { iniciarQuiz("Redes", nombreUsuario) }
        ciberseguridadButton.setOnClickListener { iniciarQuiz("Ciberseguridad", nombreUsuario) }
        microondasButton.setOnClickListener { iniciarQuiz("Microondas", nombreUsuario) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniciarQuiz(tema: String, nombreUsuario: String?) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("TEMA", tema)
        intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
        startActivity(intent)
    }
}
