package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository
import kotlinx.coroutines.launch

class DoneFitnessViewModel(application: Application): ViewModel() {
    private var daysRepository: DaysRepository = DaysRepository(application)
    val getAllDays: LiveData<List<Days>> = daysRepository.getAllDays()
    val newWeight = MutableLiveData<Double>()
    val latestDay = daysRepository.getLatestDay()


    fun updateWeight(newWeight: Double) {
        viewModelScope.launch {
            daysRepository.updateWeight(newWeight)
        }
    }

    fun getTotalCompletedExercise(): LiveData<Int> {
        return daysRepository.getTotalCompletedExercise()
    }

    fun getTotalKcalConsumed(): LiveData<Double> {
        return daysRepository.getTotalKcalConsumed()
    }

    class DoneFitnessViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DoneFitnessViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DoneFitnessViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}