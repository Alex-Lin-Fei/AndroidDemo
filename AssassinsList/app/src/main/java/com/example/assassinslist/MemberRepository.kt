package com.example.assassinslist

import android.content.Context
import androidx.room.Room
import com.example.assassinslist.dabatase.MemberDatabase
import java.lang.IllegalStateException
import java.util.*


private const val DATABASE_NAME = "member-database"
class MemberRepository private constructor(context: Context) {

    private val database: MemberDatabase = Room.databaseBuilder(
        context,
        MemberDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val memberDao = database.memberDao()

    fun getCrimes(): List<Member> = memberDao.getMembers()

    fun getCrime(id: UUID): Member? = memberDao.getMember(id)

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