package com.example.myuitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var showInfo: Button
    private lateinit var deleteInfo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.name_text)
        ageEditText = findViewById(R.id.age_text)
        heightEditText = findViewById(R.id.height_text)
        showInfo = findViewById(R.id.show_button)
        deleteInfo = findViewById(R.id.delete_button)

        deleteInfo.setOnClickListener {
            nameEditText.setText("")
            ageEditText.setText("")
            heightEditText.setText("")
        }
    }
}