package com.example.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

const val USERNAME = "username"
const val PASSWORD = "password"
const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {

    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var ensureButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameText = findViewById(R.id.username_text)
        passwordText = findViewById(R.id.password_text)
        ensureButton = findViewById(R.id.ensure_button)

        ensureButton.setOnClickListener {
            val data = Intent().apply {
                putExtra(USERNAME, usernameText.text.toString())
                putExtra(PASSWORD, passwordText.text.toString())
            }

            setResult(Activity.RESULT_OK, data)
            Log.d(TAG, "${usernameText.text}, ${passwordText.text}")
            finish()
        }
    }
}