package com.example.telequiz

data class Partida(
    val numero: Int,
    val tema: String,
    var tiempo: Long,
    var puntaje: Int,
    var estado: String
)