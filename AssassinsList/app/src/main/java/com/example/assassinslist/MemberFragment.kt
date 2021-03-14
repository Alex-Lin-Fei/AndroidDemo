package com.example.assassinslist

import android.os.Bundle
import androidx.fragment.app.Fragment

class MemberFragment: Fragment() {
    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        member = Member()
    }

}