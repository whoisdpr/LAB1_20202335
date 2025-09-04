package com.example.telequiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val finalScoreTextView: TextView = findViewById(R.id.finalScoreTextView)
        val playAgainButton: Button = findViewById(R.id.playAgainButton)

        val puntajeFinal = intent.getIntExtra("PUNTAJE_FINAL", 0)
        finalScoreTextView.text = puntajeFinal.toString()

        if (puntajeFinal >= 0) {
            finalScoreTextView.setBackgroundColor(Color.parseColor("#A5D6A7"))
        } else {
            finalScoreTextView.setBackgroundColor(Color.parseColor("#EF9A9A"))
        }

        playAgainButton.setOnClickListener {
            val intent = Intent(this, TopicSelectionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
