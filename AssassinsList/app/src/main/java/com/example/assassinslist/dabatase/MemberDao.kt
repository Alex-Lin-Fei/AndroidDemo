package com.example.assassinslist.dabatase

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Insert
    fun addMember(member: Member)
}