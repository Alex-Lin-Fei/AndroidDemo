package com.example.myuitest

import androidx.lifecycle.ViewModel

class PersonListViewModel: ViewModel() {

    private var personList = mutableListOf<Person>(
        Person("LuYanchang", 20, 176.0),
        Person("Huxin", 20, 172.0),
        )

    fun addPerson(person: Person) {
        personList.add(person)
    }

    fun getPersons() = personList

    companion object {
        private var personListViewModel: PersonListViewModel = PersonListViewModel()
        fun getInstance(): PersonListViewModel {
            return personListViewModel
        }
    }
}