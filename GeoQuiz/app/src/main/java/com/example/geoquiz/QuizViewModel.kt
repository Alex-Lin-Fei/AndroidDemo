package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel: ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var hasCommitted = mutableSetOf<Int>()
    private var marks = 1

    var currentIndex = 0
    var scores = 0

    val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
    get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun getScore() {
        scores += marks
    }

    fun answer() {
        hasCommitted.add(currentIndex)
    }

    fun hasCommitted(): Boolean {
        return hasCommitted.contains(currentIndex)
    }

    fun hasFinished(): Boolean {
        return hasCommitted.size == questionBank.size
    }
}