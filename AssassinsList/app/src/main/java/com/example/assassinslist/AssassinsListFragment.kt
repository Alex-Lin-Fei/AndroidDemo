package com.example.assassinslist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


private const val TAG = "AssassinsListFragment"
class AssassinsListFragment: Fragment() {

    interface Callbacks {
        fun onMemberSelected(memberId: UUID)
    }

    private var callbacks: Callbacks? = null


    private lateinit var assassinRecyclerView: RecyclerView
    private var adapter: MemberAdapter? = MemberAdapter(emptyList())

    private val assassinsListViewModel: AssassinsListViewModel by lazy {
        ViewModelProvider(this)[AssassinsListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        assassinRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assassinsListViewModel.assassinsListLiveData.observe(
            viewLifecycleOwner, { members ->
                members?.let {
                    updateUI(members)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(members: List<Member>) {
        adapter = MemberAdapter(members)
        assassinRecyclerView.adapter = adapter
    }


    private inner class MemberHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var member: Member
        private val nameTextView: TextView = itemView.findViewById(R.id.member_name)
        private val birthdayTextView: TextView = itemView.findViewById(R.id.member_birthday)
        private val deadImageView: ImageView = itemView.findViewById(R.id.member_dead) as ImageView

        init {
            itemView.setOnClickListener {
                callbacks?.onMemberSelected(member.id)
            }
        }

        fun bind(member: Member) {
            this.member = member
            nameTextView.text = this.member.name
            birthdayTextView.text = this.member.birthday.toString()
            deadImageView.visibility = if (member.dead) ImageView.VISIBLE else ImageView.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_member_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_member -> {
                val member = Member()
                assassinsListViewModel.addMember(member)
                callbacks?.onMemberSelected(member.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
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