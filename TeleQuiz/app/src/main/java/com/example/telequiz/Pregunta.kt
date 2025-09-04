package com.example.telequiz

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val indiceRespuestaCorrecta: Int
)