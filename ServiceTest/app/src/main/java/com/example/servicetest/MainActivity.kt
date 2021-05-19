package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var downloadBinder: MyService.DownloadBinder

    private val connection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private lateinit var startServiceBtn: Button
    private lateinit var stopServiceBtn: Button
    private lateinit var bindServiceBtn: Button
    private lateinit var unbindServiceBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startServiceBtn = findViewById(R.id.startServiceBtn)
        stopServiceBtn = findViewById(R.id.stopServiceBtn)
        bindServiceBtn = findViewById(R.id.bindServiceBtn)
        unbindServiceBtn = findViewById(R.id.unbindServiceBtn)


        startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent) // 启动服务
        }

        stopServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent) // 停止service
        }

        bindServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

    }
}