package com.example.myuitest

import androidx.lifecycle.ViewModel

class PersonListViewModel: ViewModel() {

     var personList = mutableListOf<Person>()

    fun addPerson(person: Person) {
        personList.add(person)
    }

    companion object {
        private var personListViewModel: PersonListViewModel = PersonListViewModel()
        fun getInstance(): PersonListViewModel {
            return personListViewModel
        }
    }
}