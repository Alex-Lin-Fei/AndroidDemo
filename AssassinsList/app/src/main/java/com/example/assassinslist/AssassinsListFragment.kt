package com.example.assassinslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

private const val TAG = "AssassinsListFragment"
class AssassinsListFragment: Fragment() {

    private lateinit var assassinRecyclerView: RecyclerView

    private val assassinsListViewModel: AssassinsListViewModel by lazy {
        ViewModelProvider(this)[AssassinsListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total members: ${assassinsListViewModel.members.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member_list, container, false)
        assassinRecyclerView = view.findViewById(R.id.member_recycler_view) as RecyclerView
        assassinRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    companion object {
        fun newInstance(): AssassinsListFragment {
            return AssassinsListFragment()
        }
    }
}