package com.example.dataprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(val context: Context, name: String, version: Int):
SQLiteOpenHelper(context, name, null, version){

    private val createStaff = "create table Staff (" +
            " id integer primary key autoincrement, " +
            "name text, " +
            "gender text, " +
            "department text, " +
            "salary float)"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createStaff)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Staff")
        onCreate(db)
    }
}