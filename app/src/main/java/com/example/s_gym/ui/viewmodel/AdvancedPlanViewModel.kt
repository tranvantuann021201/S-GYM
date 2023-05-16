package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AdvancedPlanViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)

    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance): Long {
        return viewModelScope.async {
            fitnessRepository.addFitnessAdvance(fitnessAdvance)
        }.await()
    }

    fun deleteEmptyFitnessAdvance() {
        viewModelScope.launch {
            fitnessRepository.deleteEmptyFitnessAdvance()
        }
    }

    class AdvancedPlanViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AdvancedPlanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AdvancedPlanViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}