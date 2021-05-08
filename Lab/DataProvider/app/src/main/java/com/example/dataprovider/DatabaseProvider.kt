package com.example.dataprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class DatabaseProvider : ContentProvider() {

    private val staffDir = 0
    private val staffItem=1
    private val authority = "com.example.dataprovider.provider"
    private lateinit var dbHelper: MyDatabaseHelper


    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "staff", staffDir)
        matcher.addURI(authority, "staff/#", staffItem)
        matcher
    }


    override fun onCreate() = context?.let {
        dbHelper = MyDatabaseHelper(it, "test.db", 1)
        true
    } ?: false

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        return when(uriMatcher.match(uri)) {
            staffDir -> db.query("Staff", projection, selection, selectionArgs, null, null, sortOrder)
            staffItem -> {
                val staffId = uri.pathSegments[1]
                db.query("Staff", projection, "id = ?", arrayOf(staffId), null, null, sortOrder)
            }
            else-> null
        }
    }

    override fun getType(uri: Uri) = when(uriMatcher.match(uri)) {
        staffDir -> "vnd.android.cursor.dir/vnd.com.example.dataprovider.provider.staff"
        staffItem -> "vnd.android.cursor.item/vnd.com.example.dataprovider.provider.staff"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?) = dbHelper.let {
        val db = it.writableDatabase
        val uriReturn = when (uriMatcher.match(uri)) {
            staffItem, staffDir -> {
                val newStaffId = db.insert("Staff", null, values)
                Uri.parse("content://$authority/book/$newStaffId")
            }
            else -> null
        }
        uriReturn
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = dbHelper.let {
        val db = it.writableDatabase
        val deleteRows = when(uriMatcher.match(uri)) {
            staffDir -> db.delete("Staff", selection, selectionArgs)
            staffItem -> {
                val staffId = uri.pathSegments[1]
                db.delete("Staff", "id = ?", arrayOf(staffId))
            }
            else -> 0
        }
        deleteRows
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = dbHelper.let {
        val db = dbHelper.writableDatabase
        val updateRows = when(uriMatcher.match(uri)) {
            staffDir -> db.update("Staff", values, selection, selectionArgs)
            staffItem -> {
                val staffId = uri.pathSegments[1]
                db.update("Staff", values, "id = ?", arrayOf(staffId))
            }
            else -> 0
        }
        updateRows
    }
}