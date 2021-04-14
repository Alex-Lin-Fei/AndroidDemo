package com.example.myapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

private const val TAG = "MainActivity"
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
                put("price", 19.95)
            }
            db.insert("Book", null, values2)
        }

        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name=?", arrayOf("The Da Vinci Code"))
            Toast.makeText(this, "update succeeded", Toast.LENGTH_SHORT).show()
        }


        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages>?", arrayOf("500"))
        }

        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d(TAG, "book name is $name")
                    Log.d(TAG, "book author is $author")
                    Log.d(TAG, "book pages is $pages")
                    Log.d(TAG, "book price is $price")

                } while (cursor.moveToNext())
            }
            cursor.close()
        }
    }
}