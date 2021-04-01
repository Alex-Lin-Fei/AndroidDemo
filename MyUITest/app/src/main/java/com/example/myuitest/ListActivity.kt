package com.example.myuitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    private lateinit var personRecyclerView: RecyclerView

    private val personListViewModel: PersonListViewModel = PersonListViewModel.getInstance()
    private var adapter: PersonAdapter? = PersonAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        personRecyclerView = findViewById(R.id.person_recycler_view)
        personRecyclerView.layoutManager = LinearLayoutManager(this)
        personRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        adapter = PersonAdapter(personListViewModel.getPersons())
        personRecyclerView.adapter = adapter
    }

    private inner class PersonHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var person: Person

        private val nameTextView: TextView = itemView.findViewById(R.id.person_name)
        private val ageAndHeightTextView: TextView = itemView.findViewById(R.id.person_age_height)


        fun bind(person: Person) {
            this.person = person
            nameTextView.text = person.name
            ageAndHeightTextView.text = getString(R.string.person_age_height, person.age.toString(), person.height.toString())
        }

    }

    private inner class PersonAdapter(var persons: List<Person>) :
        RecyclerView.Adapter<PersonHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return PersonHolder(view)
        }

        override fun onBindViewHolder(holder: PersonHolder, position: Int) {
            val person = persons[position]

            holder.bind(person)
        }

        override fun getItemCount() = persons.size
    }

}