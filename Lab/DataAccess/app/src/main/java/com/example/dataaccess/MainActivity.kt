package com.example.dataaccess

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.contentValuesOf

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    //    需要添加的数据
    private val staffs = listOf(
            Staff("Tom", "male", "computer", 5400.0),
            Staff("Einstein", "male", "computer", 4800.0),
            Staff("Lily", "female", "market", 5000.0),
            Staff("Warner", "male", "market"),
            Staff("Napoleon", "male"),
    )

    var staffId: String? = null
    private var uriString = "content://com.example.dataprovider.provider/"
    private val database = "staff"

    private lateinit var addData: Button
    private lateinit var queryData: Button
    private lateinit var updateData: Button
    private lateinit var deleteData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addData = findViewById(R.id.addData)
        queryData = findViewById(R.id.queryData)
        updateData = findViewById(R.id.updateData)
        deleteData = findViewById(R.id.deleteData)


        addData.setOnClickListener {
            val uri = Uri.parse(uriString + database)
            for (staff in staffs) {
                val values = contentValuesOf("name" to staff.name, "gender" to staff.gender,
                        "department" to staff.department, "salary" to staff.salary)
                val newUri = contentResolver.insert(uri, values)
                staffId = newUri?.pathSegments?.get(1)
            }
            Toast.makeText(this, "Add Data successfully", Toast.LENGTH_SHORT).show()
        }

        queryData.setOnClickListener {
            val uri = Uri.parse(uriString + database)
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()) {
                    val name = getString(getColumnIndex("name"))
                    val gender = getString(getColumnIndex("gender"))
                    val department = getString(getColumnIndex("department"))
                    val salary = getDouble(getColumnIndex("salary"))
                    Log.d(TAG, "staff name: $name")
                    Log.d(TAG, "staff gender: $gender")
                    Log.d(TAG, "staff department: $department")
                    Log.d(TAG, "staff salary: $salary")
                }
                close()
            }
        }

        updateData.setOnClickListener {

        }

        deleteData.setOnClickListener {

        }
    }
}