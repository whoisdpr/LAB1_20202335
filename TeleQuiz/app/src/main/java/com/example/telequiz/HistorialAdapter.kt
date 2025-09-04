package com.example.telequiz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialAdapter(private val partidas: List<Partida>) :
    RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.historialItemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partida = partidas[position]
        var displayText = "Juego ${partida.numero}: "

        when (partida.estado) {
            "Terminada" -> {
                displayText += "${partida.tema} | Tiempo: ${partida.tiempo}s | Puntaje: ${partida.puntaje}"
                if (partida.puntaje >= 0) {
                    holder.textView.setTextColor(Color.parseColor("#388E3C"))
                } else {
                    holder.textView.setTextColor(Color.parseColor("#D32F2F"))
                }
            }
            "Canceló" -> {
                displayText += "Canceló"
                holder.textView.setTextColor(Color.GRAY)
            }
            "En curso" -> {
                displayText += "${partida.tema} | En curso..."
                holder.textView.setTextColor(Color.parseColor("#1976D2"))
            }
        }

        holder.textView.text = displayText
    }

    override fun getItemCount() = partidas.size
}
