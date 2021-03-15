package com.example.assassinslist

import android.content.Context
import java.lang.IllegalStateException

class MemberRepository private constructor(context: Context) {
    companion object {
        private var INSTANCE: MemberRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MemberRepository(context)
            }
        }

        fun get(): MemberRepository {
            return INSTANCE?:throw IllegalStateException("MemberRepository must be initialized")
        }
    }
}