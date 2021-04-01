package com.example.myuitest

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_NAME = "name"
private const val EXTRA_AGE = "age"
private const val EXTRA_HEIGHT = "height"
class InfoActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var age: String
    private lateinit var height: String

    private lateinit var infoTextView: TextView
    private lateinit var returnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        infoTextView = findViewById(R.id.info)
        returnButton = findViewById(R.id.return_button)

        name = intent.getStringExtra(EXTRA_NAME).toString()
        age = intent.getStringExtra(EXTRA_AGE).toString()
        height = intent.getStringExtra(EXTRA_HEIGHT).toString()

        infoTextView.text = getString(R.string.info, name, age, height)
        returnButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    companion object {
        fun newIntent(packageContext: Context, name: String, age: Int, height: Double): Intent {
            return Intent(packageContext, InfoActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_AGE, age)
                putExtra(EXTRA_HEIGHT, height)
            }
        }
    }
}