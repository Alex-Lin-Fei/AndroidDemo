package com.example.dataaccess

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.content.contentValuesOf
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.thread

private const val TAG = "MyService"
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate executed")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand executed")
        thread {
            while (true) {
//            循环检查所有数据
                val uri = Uri.parse(MainActivity.uriString + MainActivity.database)
                contentResolver.query(uri, null, null, null, null)?.apply {
                    while (moveToNext()) {
                        val id = getString(getColumnIndex("id"))
                        val name = getString(getColumnIndex("name"))
                        val gender = getString(getColumnIndex("gender"))
                        val department = getString(getColumnIndex("department"))
                        val salary = getDouble(getColumnIndex("salary"))
                        if (salary < 0.0) {
                            val values = contentValuesOf(
                                "name" to name, "gender" to gender, "department" to department,
                                "salary" to 0.0
                            )
                            id.let {
                                val updateUri =
                                    Uri.parse("${MainActivity.uriString}${MainActivity.database}/$it")
                                contentResolver.update(updateUri, values, null, null)
                                Log.d(TAG, "set $id: $name`s salary to zero") //控制台显示
//                                写入文件

//                                val time = SimpleDateFormat("yyyy:MM:dd hh:mm:ss").format(
//                                    LocalDateTime)
                                val formatter = SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
                                val curDate = Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8)
                                val time = formatter.format(curDate)

                                save("$time $id")
                            }
                        }
                    }
                    close()
                }
                Thread.sleep(10000)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy executed")
    }

    private fun save(text: String) {
        try {
            val output = openFileOutput("check.log", Context.MODE_APPEND)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(text+"\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

