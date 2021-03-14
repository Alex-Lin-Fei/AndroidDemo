package com.example.assassinslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.math.log

private const val TAG = "AssassinsListFragment"
class AssassinsListFragment: Fragment() {
    private val assassinsListViewModel: AssassinsListViewModel by lazy {
        ViewModelProvider(this)[AssassinsListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total members: ${assassinsListViewModel.members.size}")
    }

    companion object {
        fun newInstance(): AssassinsListFragment {
            return AssassinsListFragment()
        }
    }
}