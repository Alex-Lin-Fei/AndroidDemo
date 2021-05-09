package com.example.dataprovider

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
import java.sql.BatchUpdateException


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //    需要保存在sharedpreferens中的数据
//    背景颜色的r g b
    private var bgR = 255
    private var bgG = 255
    private var bgB = 255

    //    字体颜色 R G B
    private var textR = 0
    private var textG = 0
    private var textB = 0

    //    字体大小
    private var fontSize: Float = 15.0F


    //    布局中的对象
    private lateinit var bgRText: EditText
    private lateinit var bgGText: EditText
    private lateinit var bgBText: EditText
    private lateinit var tRText: EditText
    private lateinit var tGText: EditText
    private lateinit var tBText: EditText

    private lateinit var fontSizeText: EditText
    private lateinit var saveBtn: Button


    private lateinit var staffTable: TableLayout
    private lateinit var dbHelper: MyDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bgRText = findViewById(R.id.bgR_text)
        bgGText = findViewById(R.id.bgG_text)
        bgBText = findViewById(R.id.bgB_text)

        tRText = findViewById(R.id.tR_text)
        tGText = findViewById(R.id.tG_text)
        tBText = findViewById(R.id.tB_text)

        fontSizeText = findViewById(R.id.font_size_text)
        saveBtn = findViewById(R.id.save_btn)


//        读取sharedPreferences中的配置信息
        readSettingData()

        saveBtn.setOnClickListener {
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            if (bgRText.text.toString().isNotEmpty())
                editor.putInt("bgR", bgRText.text.toString().toInt().coerceAtMost(255))
            if (bgGText.text.toString().isNotEmpty())
                editor.putInt("bgG", bgGText.text.toString().toInt().coerceAtMost(255))
            if (bgBText.text.toString().isNotEmpty())
                editor.putInt("bgB", bgBText.text.toString().toInt().coerceAtMost(255))
            if (tRText.text.toString().isNotEmpty())
                editor.putInt("textR", tRText.text.toString().toInt().coerceAtMost(255))
            if (tGText.text.toString().isNotEmpty())
                editor.putInt("textG", tGText.text.toString().toInt().coerceAtMost(255))
            if (tBText.text.toString().isNotEmpty())
                editor.putInt("textB", tBText.text.toString().toInt().coerceAtMost(255))
            if (fontSizeText.toString().isNotEmpty())
                editor.putFloat("fontSize", fontSizeText.text.toString().toFloat())
            editor.apply()

            Toast.makeText(this, "save setting data successfully", Toast.LENGTH_SHORT).show()
        }

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
        textView.setBackgroundColor(Color.rgb(bgR, bgG, bgB))
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.rgb(textR, textG, textB))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textView.width = 260
        return textView
    }

    private fun readSettingData() {
        val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
        bgR = prefs.getInt("bgR", 255)
        bgG = prefs.getInt("bgG", 255)
        bgB = prefs.getInt("bgB", 255)
        textR = prefs.getInt("textR", 0)
        textG = prefs.getInt("textG", 0)
        textB = prefs.getInt("textB", 0)
        fontSize = prefs.getFloat("fontSize", 15.0F)
    }
}

