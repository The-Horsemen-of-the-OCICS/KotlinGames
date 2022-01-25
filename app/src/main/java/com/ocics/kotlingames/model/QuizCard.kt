package com.ocics.kotlingames.model

data class QuizCard(
    val question: String,
    val choice: ArrayList<String>,
    val answer: Int
)
