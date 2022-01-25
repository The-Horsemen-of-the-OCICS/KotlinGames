package com.ocics.kotlingames.viewmodel

import androidx.lifecycle.ViewModel
import com.ocics.kotlingames.model.QuizCard

class QuizViewModel: ViewModel() {
    val cards: MutableList<QuizCard> = mutableListOf()
    val selections: MutableList<Int> = mutableListOf()

    fun getScore(): Int {
        var score = 0
        for ((index, card) in cards.withIndex()) {
            if (card.answer == selections[index]) score++
        }
        return score
    }

    fun addCard(q: QuizCard) {
        cards.add(q)
        shuffleCards()
    }

    fun shuffleCards() {
        if (cards.isNotEmpty()) {
            cards.shuffle()
        }
    }

    fun reset() {
        cards.clear()
        selections.clear()
        loadDefaultQuestions()
    }

    fun loadDefaultQuestions() {
        cards.add(
            QuizCard(
            "Such devastation, this was ...",
            arrayListOf("not my intention", "exactly my intention", "kind of my intention", "undoubtedly your intention"),
                0))

        cards.add(
            QuizCard(
                "A test of your ...",
                arrayListOf("Endurance", "Courage", "Strength", "Reflexes"),
                3))

        cards.add(
            QuizCard(
                "Where does storm blood born from?",
                arrayListOf("Blood of our fallen brothers", "Blood of our doomed families",
                    "Tear of the walking sands", "Paint of the rising stones"),
                0))

        cards.add(
            QuizCard(
                "What is the true name of Fandaniel?",
                arrayListOf("Themis", "Hades", "Hermes", "Venat"),
                2))

        cards.add(
            QuizCard(
                "What is the most common strategy used in Hydaelyn Extreme? ",
                arrayListOf("H/D/T G1 in G2 out", "D/H/T G1 in G2 out", "T/H/D G1 out G2 in", "T/D/H G1 in G2 out"),
                0))

        cards.shuffle()
    }
}