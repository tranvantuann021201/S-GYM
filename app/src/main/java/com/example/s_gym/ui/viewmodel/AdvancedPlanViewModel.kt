package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdvancedPlanViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    val allFitness: LiveData<List<FitnessAdvance>> = fitnessRepository.readAllData()
    private var daysRepository: DaysRepository = DaysRepository(application)

    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance): Long {
        return withContext(viewModelScope.coroutineContext) {
            fitnessRepository.addFitnessAdvance(fitnessAdvance)
        }
    }

    fun deleteEmptyFitnessAdvance() {
        viewModelScope.launch {
            fitnessRepository.deleteEmptyFitnessAdvance()
        }
    }

    fun deleteAllFromFitnessAdvance(){
        viewModelScope.launch {
            fitnessRepository.deleteAllFromFitnessAdvance()
//            daysRepository.deleteAllFromDays()
        }
    }

    fun deleteFitnessAdvance(fitnessAdvance: FitnessAdvance) {
        viewModelScope.launch {
            fitnessRepository.deleteFitnessAdvance(fitnessAdvance)
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