package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(QuizViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

        trueButton.isEnabled = !quizViewModel.hasCommitted()
        falseButton.isEnabled = !quizViewModel.hasCommitted()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        quizViewModel.answer()
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (correctAnswer == userAnswer) {
            quizViewModel.getScore()
            R.string.correct_toast
        }else
            R.string.incorrect_toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        trueButton.isEnabled = !quizViewModel.hasCommitted()
        falseButton.isEnabled = !quizViewModel.hasCommitted()

        if (quizViewModel.hasFinished()) {
            Toast.makeText(this, "You have gotten ${quizViewModel.scores} scores", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called isFinishing == $isFinishing")
    }
}