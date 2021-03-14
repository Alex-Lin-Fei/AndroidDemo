package com.example.assassinslist

import java.util.*

data class Member(
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var gender: Gender = Gender.MALE,
    var birthday: Date = Date(),
    var information: String = "",
    var dead: Boolean = false
) {
}