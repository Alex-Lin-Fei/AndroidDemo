package com.example.dataprovider

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var staffTable: TableLayout
    private lateinit var dbHelper: MyDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = MyDatabaseHelper(this, "test.db", 1)
        staffTable = findViewById(R.id.staff_table)

        dbHelper.writableDatabase

//        query  database and show in table
        showData()

    }

    private fun showData() {
        val db = dbHelper.writableDatabase

        val cursor = db.query("Staff", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val gender = cursor.getString(cursor.getColumnIndex("gender"))
                val department = cursor.getString(cursor.getColumnIndex("department"))
                val salary = cursor.getDouble(cursor.getColumnIndex("salary"))
//                添加到表格

                val item: TableRow = TableRow(applicationContext)

                item.addView(getTextView(name))
                item.addView(getTextView(gender))
                item.addView(getTextView(department))
                item.addView(getTextView(salary.toString()))

                Log.d(TAG, "${item.size}")
                staffTable.addView(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        Toast.makeText(this, "show data successfully", Toast.LENGTH_SHORT).show()
    }

    private fun getTextView(text: String): TextView {
        val textView: TextView = TextView(applicationContext)
        textView.text = text
        textView.setBackgroundColor(Color.rgb(255,255, 255))
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.rgb(0,0, 0))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0F)
        textView.width = 260
        return textView
    }
}