package com.example.assassinslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class MemberDetailViewModel: ViewModel() {
    private val memberRepository = MemberRepository.get()
    private val memberIdLiveData = MutableLiveData<UUID>()

    val memberLiveDate: LiveData<Member?> =
        Transformations.switchMap(memberIdLiveData) { memberId ->
            memberRepository.getMember(memberId)
        }

    fun loadMember(memberId: UUID) {
        memberIdLiveData.value = memberId
    }
}