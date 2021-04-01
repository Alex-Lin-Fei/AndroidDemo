package com.example.myuitest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

private const val EXTRA_NAME = "name"
private const val EXTRA_AGE = "age"
private const val EXTRA_HEIGHT = "height"
class InfoActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var age: String
    private lateinit var height: String

    private lateinit var InfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        InfoTextView = findViewById(R.id.info)

        name = intent.getStringExtra(EXTRA_NAME).toString()
        age = intent.getStringExtra(EXTRA_AGE).toString()
        height = intent.getStringExtra(EXTRA_HEIGHT).toString()

        InfoTextView.text = getString(R.string.info, name, age, height)
    }

    companion object {
        fun newIntent(packageContext: Context, name: String, age: String, height: String): Intent {
            return Intent(packageContext, InfoActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_AGE, age)
                putExtra(EXTRA_HEIGHT, height)
            }
        }
    }
}