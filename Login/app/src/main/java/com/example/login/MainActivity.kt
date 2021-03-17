package com.example.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView


private const val REQUEST_CODE = 0
class MainActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeText = findViewById<TextView>(R.id.welcome_text)
        loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, LoginActivity::class.java), REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.d(TAG, "success")

//        if (resultCode != Activity.RESULT_OK) {
//            return
//        }

        if (requestCode == REQUEST_CODE) {
            val username: String? = data?.getStringExtra(USERNAME)
            val password : String? = data?.getStringExtra(PASSWORD)
            welcomeText.text = getString(R.string.welcome, username, password)
            Log.d(TAG, "success")
        }
    }
}