package com.example.dataprovider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var staffTable: TableLayout
    private lateinit var dbHelper: MyDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = MyDatabaseHelper(this, "test.db", 1)
        staffTable = findViewById(R.id.staff_table)

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
                val nameTextView: TextView = TextView(applicationContext)
                val genderTextView: TextView = TextView(applicationContext)
                val departmentTextView: TextView = TextView(applicationContext)
                val salaryTextView: TextView = TextView(applicationContext)


                nameTextView.text = name
                genderTextView.text = gender
                departmentTextView.text = department
                salaryTextView.text = salary.toString()

                item.addView(nameTextView)
                item.addView(genderTextView)
                item.addView(departmentTextView)
                item.addView(salaryTextView)

                staffTable.addView(item)

            } while (cursor.moveToNext())
        }
        cursor.close()
        Toast.makeText(this, "show data successfully", Toast.LENGTH_SHORT).show()
    }
}