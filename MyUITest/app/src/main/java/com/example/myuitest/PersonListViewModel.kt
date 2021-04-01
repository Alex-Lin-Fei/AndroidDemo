package com.example.myuitest

import androidx.lifecycle.ViewModel

class PersonListViewModel: ViewModel() {
    private var personList = mutableListOf<Person>()
}