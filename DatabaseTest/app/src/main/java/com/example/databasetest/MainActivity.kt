package com.example.databasetest

import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf

class MainActivity : AppCompatActivity() {

    private lateinit var createDatabase: Button
    private lateinit var addData: Button
    private lateinit var updateData: Button
    private lateinit var queryData: Button
    private lateinit var deleteData: Button
    private lateinit var replaceData: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 3)

        createDatabase = findViewById(R.id.createDatabase)
        addData = findViewById(R.id.addData)
        updateData = findViewById(R.id.updateData)
        queryData = findViewById(R.id.queryData)
        deleteData = findViewById(R.id.deleteData)
        replaceData = findViewById(R.id.replaceData)

        createDatabase.setOnClickListener {
            dbHelper.writableDatabase
            Toast.makeText(this, "create database successfully", Toast.LENGTH_SHORT).show()
        }
        contentValuesOf("" to "")
        addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                // 开始组装第一条数据
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1) // 插入第一条数据
            val values2 = ContentValues().apply {
                // 开始组装第二条数据
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 19.95)
            }
            db.insert("Book", null, values2) // 插入第二条数据
            Toast.makeText(this, "add data successfully", Toast.LENGTH_SHORT).show()
        }
        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            val rows = db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
            Toast.makeText(this, "rows is $rows", Toast.LENGTH_SHORT).show()
        }
        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
            Toast.makeText(this, "delete data successfully", Toast.LENGTH_SHORT).show()
        }
        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询Book表中所有的数据
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    // 遍历Cursor对象，取出数据并打印
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d("MainActivity", "book name is $name")
                    Log.d("MainActivity", "book author is $author")
                    Log.d("MainActivity", "book pages is $pages")
                    Log.d("MainActivity", "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()
            Toast.makeText(this, "query data successfully", Toast.LENGTH_SHORT).show()
        }
        replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction() // 开启事务
            try {
                db.delete("Book", null, null)
//                if (true) {
//                    // 在这里手动抛出一个异常，让事务失败
//                    throw NullPointerException()
//                }
                val values = cvOf("name" to "Game of Thrones", "author" to "George Martin", "pages" to 720, "price" to 20.85)
                db.insert("Book", null, values)
                db.setTransactionSuccessful() // 事务已经执行成功
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction() // 结束事务
            }
        }
    }

    private fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply {
        for (pair in pairs) {
            val key = pair.first
            val value = pair.second
            when(value) {
                is Int -> put(key, value)
                is Long -> put(key, value)
                is Short -> put(key, value)
                is Float -> put(key, value)
                is Double -> put(key, value)
                is Boolean -> put(key, value)
                is String -> put(key, value)
                is Byte -> put(key, value)
                is ByteArray -> put(key, value)
                null -> putNull(key)
            }
        }
    }

}
