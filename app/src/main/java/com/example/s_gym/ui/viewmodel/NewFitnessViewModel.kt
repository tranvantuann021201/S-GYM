package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch

class NewFitnessViewModel(application: Application): ViewModel() {
    val exercisesListLiveData = MutableLiveData<List<Exercises>>()
    private val _fitnessAdvanceName: MutableLiveData<String> = MutableLiveData()
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)

    class NewFitnessViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NewFitnessViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewFitnessViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}