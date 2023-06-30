package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository

class HistoryViewModel(application: Application): ViewModel() {
    private var daysRepository: DaysRepository = DaysRepository(application)
    val currentUser = MainActivity.currentFirebaseUser
    val getAllDays: LiveData<List<Days>> = daysRepository.getAllDays(currentUser!!.uid)

    class HistoryViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}