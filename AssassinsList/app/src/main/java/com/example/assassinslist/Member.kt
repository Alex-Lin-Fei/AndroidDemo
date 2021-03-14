package com.example.assassinslist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Member(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var gender: Gender = Gender.MALE,
    var birthday: Date = Date(),
    var information: String = "",
    var dead: Boolean = false
) {
}