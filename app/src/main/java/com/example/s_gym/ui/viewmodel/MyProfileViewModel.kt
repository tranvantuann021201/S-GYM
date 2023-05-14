package com.example.s_gym.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyProfileViewModel: ViewModel() {
    val selectedDate = MutableLiveData<String>()

    fun updateSelectedDate(date: String) {
        selectedDate.value = date
    }

}