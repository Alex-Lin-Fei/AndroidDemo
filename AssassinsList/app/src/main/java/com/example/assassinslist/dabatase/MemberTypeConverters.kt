package com.example.assassinslist.dabatase

import androidx.room.TypeConverter
import java.util.*

class MemberTypeConverters {
    @TypeConverter
    fun fromDate(birthday: Date?): Long? {
        return birthday?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
}
