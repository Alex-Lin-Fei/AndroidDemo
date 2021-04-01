package com.example.myuitest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private const val SHOW_INFO = 1

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

        showInfo.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val height = heightEditText.text.toString()
            val intent = InfoActivity.newIntent(this@MainActivity, name, age, height)
            startActivityForResult(intent, SHOW_INFO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == SHOW_INFO) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu).apply {
            menuInflater.inflate(R.menu.my_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item).apply {
            Toast.makeText(this@MainActivity, "You have click menu ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }
}