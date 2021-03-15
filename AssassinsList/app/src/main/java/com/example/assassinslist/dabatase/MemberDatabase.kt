package com.example.assassinslist.dabatase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.assassinslist.Member

@Database(entities = [Member::class], version = 1)
@TypeConverters(MemberTypeConverters::class)
abstract class MemberDatabase: RoomDatabase() {
    abstract fun memberDao(): MemberDao
}