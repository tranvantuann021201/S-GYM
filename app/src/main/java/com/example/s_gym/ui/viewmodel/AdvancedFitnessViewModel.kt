package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch

class AdvancedFitnessViewModel(application: Application) : AndroidViewModel(application) {
    private val fitnessRepository = FitnessAdvanceRepository(application)
    val fitnessAdvanceLiveData = MutableLiveData<FitnessAdvance>()

    fun updateFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        viewModelScope.launch {
            fitnessRepository.updateFitnessAdvance(fitnessAdvance)
            fitnessAdvanceLiveData.value = fitnessAdvance
        }
    }
}