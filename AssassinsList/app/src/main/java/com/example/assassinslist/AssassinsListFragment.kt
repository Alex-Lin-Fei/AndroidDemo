package com.example.assassinslist

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.math.log

private const val TAG = "AssassinsListFragment"
class AssassinsListFragment: Fragment() {

    interface Callbacks {
        fun onMemberSelected(memberId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var assassinRecyclerView: RecyclerView
//    private var adapter: MemberAdapter? = null

    private val assassinsListViewModel: AssassinsListViewModel by lazy {
        ViewModelProvider(this)[AssassinsListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member_list, container, false)
        assassinRecyclerView = view.findViewById(R.id.member_recycler_view) as RecyclerView
        assassinRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI() {
        val members = assassinsListViewModel.members
        assassinRecyclerView.adapter = MemberAdapter(members)
    }

    private inner class MemberHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var member: Member
        private val nameTextView: TextView = itemView.findViewById(R.id.member_name)
        private val birthdayTextView: TextView = itemView.findViewById(R.id.member_birthday)
        private val deadImageView: ImageView = itemView.findViewById(R.id.member_dead) as ImageView

        init {
            itemView.setOnClickListener {
                Toast.makeText(context, "${member.name} pressed", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(member: Member) {
            this.member = member
            nameTextView.text = this.member.name
            birthdayTextView.text = this.member.birthday.toString()
            deadImageView.visibility = if (member.dead) ImageView.VISIBLE else ImageView.GONE
        }
    }

    private inner class MemberAdapter(var members: List<Member>): RecyclerView.Adapter<MemberHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberHolder {
            val view = layoutInflater.inflate(R.layout.list_item_member, parent, false)

            return MemberHolder(view)
        }

        override fun onBindViewHolder(holder: MemberHolder, position: Int) {
            val member = members[position]
            holder.bind(member)
        }

        override fun getItemCount() = members.size
    }

    companion object {
        fun newInstance(): AssassinsListFragment {
            return AssassinsListFragment()
        }
    }
}