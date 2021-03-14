package com.example.assassinslist

import androidx.lifecycle.ViewModel

class AssassinsListViewModel: ViewModel() {
    val members = mutableListOf<Member>()

    init {
        for (i in 0 until 100) {
            val member = Member()
            member.name = "Member #$i"
            member.gender = if (i % 3 == 0 && i % 6 != 0) Gender.MALE else Gender.FEMALE
            member.dead = i % 2 == 0
            member.information = "This is Member #$i"
        }
    }
}