package com.example.assassinslist.dabatase

import androidx.room.TypeConverter
import com.example.assassinslist.Gender
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
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromGender(gender: Gender?): String? {
        return gender?.toString()
    }

    @TypeConverter
    fun toGender(gender: String?): Gender? {
        return gender?.let { enumValueOf<Gender>(it) }
    }
}
