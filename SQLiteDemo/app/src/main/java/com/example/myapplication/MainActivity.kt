package com.example.myapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var createDatabase: Button
    private lateinit var addData: Button
    private lateinit var updateData: Button
    private lateinit var deleteData: Button
    private lateinit var queryData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createDatabase = findViewById(R.id.createDatabase)
        addData = findViewById(R.id.addData)
        updateData = findViewById(R.id.updateData)
        deleteData = findViewById(R.id.deleteData)
        queryData = findViewById(R.id.queryData)

        val dbHelper = DatabaseHelper(this, "BookStore.db", 2)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }

            db.insert("Book", null, values1)
            val values2 = ContentValues().apply {
                put("name", "The lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 510)
                put("price", 19.95)
            }
            db.insert("Book", null, values2)
        }
    }

}