package com.example.assassinslist.dabatase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assassinslist.Member

@Database(entities = [Member::class], version = 1)
abstract class MemberDatabase: RoomDatabase() {

}