package com.example.myuitest

import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "List Activity"
class ListActivity : AppCompatActivity() {

    private lateinit var personRecyclerView: RecyclerView

    private val personListViewModel: PersonListViewModel = PersonListViewModel.getInstance()
//    private var adapter: PersonAdapter? = PersonAdapter(MutableList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Create")
        setContentView(R.layout.activity_list)

        personRecyclerView = findViewById(R.id.person_recycler_view)
        personRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        personRecyclerView.adapter = PersonAdapter(personListViewModel.getPersons())
        Log.d(TAG, "${personListViewModel.getPersons().size}")
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

        init {
            itemView.setOnClickListener {
                val intent = InfoActivity.newIntent(this@ListActivity, person.name, person.age, person.height)
                startActivity(intent)
            }
        }
    }

    private inner class PersonAdapter(var persons: MutableList<Person>) :
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