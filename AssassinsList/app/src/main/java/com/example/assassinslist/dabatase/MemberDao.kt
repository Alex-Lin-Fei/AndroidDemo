package com.example.assassinslist.dabatase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Update
import com.example.assassinslist.Member
import java.util.*

@Dao
interface MemberDao {
    @Query("SELECT * FROM Member")
    fun getMembers(): LiveData<List<Member>>

    @Query("SELECT * FROM Member WHERE id = (:id)")
    fun getMember(id: UUID): LiveData<Member?>

    @Update
    fun updateMember(member: Member)

    @Update
    fun addMember(member: Member)
}