package com.example.criminalintent

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
//    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frame_crime_list, container, false)
        crimeRecyclerView =
                view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
//        updateUI()

        return view
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
                viewLifecycleOwner,
                { crimes->
                    crimes?.let {
                        Log.i(TAG, "Got crimes ${crimes.size}")
                        updateUI(crimes)
                    }
                }
        )
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
//            dateTextView.text = this.crime.date.toString()
            dateTextView.text = SimpleDateFormat("yy-mm-dd-mm-ss").format(crime.date)
            solvedImageView.visibility = if (crime.isSolved) View.VISIBLE else View.GONE
        }

        init {
            val clickListener = View.OnClickListener { callbacks?.onCrimeSelected(crime.id) }
            itemView.setOnClickListener(clickListener)
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
            RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]

            holder.bind(crime)
        }

        override fun getItemCount() = crimes.size
    }
}