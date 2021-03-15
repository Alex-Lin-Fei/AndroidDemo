package com.example.assassinslist

import android.app.Application
import androidx.appcompat.view.menu.MenuItemWrapperICS
import java.lang.Appendable

class AssassinsListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MemberRepository.initialize(this)
    }
}