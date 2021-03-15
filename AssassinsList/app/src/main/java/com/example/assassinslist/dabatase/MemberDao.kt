package com.example.assassinslist.dabatase

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import com.example.assassinslist.Member
import java.util.*

@Dao
interface MemberDao {
    @Query("SELECT * FROM Member")
    fun getMembers(): List<Member>

    @Query("SELECT * FROM Member WHERE id = (:id)")
    fun getMember(id: UUID): Member?
}