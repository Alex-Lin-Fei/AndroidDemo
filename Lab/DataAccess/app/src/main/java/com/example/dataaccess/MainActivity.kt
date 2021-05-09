package com.example.dataaccess

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.contentValuesOf

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    var staffId: String? = null

    companion object {
        var uriString = "content://com.example.dataprovider.provider/"
        val database = "staff"
    }

    private lateinit var addData: Button
    private lateinit var queryData: Button
    private lateinit var updateData: Button
    private lateinit var deleteData: Button
    private lateinit var nameText: EditText
    private lateinit var genderText: EditText
    private lateinit var departmentText: EditText
    private lateinit var salaryText: EditText
    private lateinit var idText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData = findViewById(R.id.add_data_btn)
        queryData = findViewById(R.id.query_data_btn)
        updateData = findViewById(R.id.update_data_btn)
        deleteData = findViewById(R.id.delete_data_btn)

        idText = findViewById(R.id.id_text)
        nameText = findViewById(R.id.name_text)
        genderText = findViewById(R.id.gender_text)
        departmentText = findViewById(R.id.department_text)
        salaryText = findViewById(R.id.salary_text)

//        启动service
        val intent = Intent(this, MyService::class.java)
        startService(intent)


        addData.setOnClickListener {
            val uri = Uri.parse(uriString + database)
            val name = nameText.text.toString()
            val gender = genderText.text.toString()
            val department = departmentText.text.toString()
            val salary = salaryText.text.toString().toDouble()

            val values = contentValuesOf(
                "name" to name, "gender" to gender,
                "department" to department, "salary" to salary
            )
            val newUri = contentResolver.insert(uri, values)
            staffId = newUri?.pathSegments?.get(1)
            Toast.makeText(this, "Add data successfully", Toast.LENGTH_SHORT).show()
        }

        queryData.setOnClickListener {
            val uri = Uri.parse(uriString + database)
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()) {
                    val id = getString(getColumnIndex("id"))
                    val name = getString(getColumnIndex("name"))
                    val gender = getString(getColumnIndex("gender"))
                    val department = getString(getColumnIndex("department"))
                    val salary = getDouble(getColumnIndex("salary"))
                    Log.d(TAG, "staff id: $id")
                    Log.d(TAG, "staff name: $name")
                    Log.d(TAG, "staff gender: $gender")
                    Log.d(TAG, "staff department: $department")
                    Log.d(TAG, "staff salary: $salary")
                }
                close()
            }
            Toast.makeText(this, "Query data successfully", Toast.LENGTH_SHORT).show()
        }

        updateData.setOnClickListener {
            val staffId = idText.text.toString().toInt()
            val name = nameText.text.toString()
            val gender = genderText.text.toString()
            val department = departmentText.text.toString()
            val salary = salaryText.text.toString().toDouble()

            val values = contentValuesOf("name" to name, "gender" to gender, "department" to department,
            "salary" to salary)
            staffId.let {
                val uri = Uri.parse("$uriString$database/$it")
                contentResolver.update(uri, values, null, null)
                Toast.makeText(this, "Update data successfully", Toast.LENGTH_SHORT).show()
            }
        }

        deleteData.setOnClickListener {
            val staffId = idText.text.toString().toInt()
            staffId.let {
                val uri = Uri.parse("$uriString$database/$it")
                contentResolver.delete(uri, null, null)
                Toast.makeText(this, "Delete data successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
//        停止service
        val intent = Intent(this, MyService::class.java)
        stopService(intent)

        super.onDestroy()
    }
}