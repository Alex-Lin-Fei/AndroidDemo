package com.example.androidthreadtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val updateText = 1
    val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                updateText -> textView.text = "Nice to meet you"
            }
        }
    }

    private lateinit var changeTextBtn: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeTextBtn = findViewById(R.id.changeTextBtn)
        textView = findViewById(R.id.textView)

        changeTextBtn.setOnClickListener {
            thread {
                val msg = Message()
                msg.what = updateText
                handler.sendMessage(msg)
            }
        }
    }
}