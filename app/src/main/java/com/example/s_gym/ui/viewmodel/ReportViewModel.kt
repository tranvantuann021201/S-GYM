package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository
import kotlinx.coroutines.launch

class ReportViewModel(application: Application): ViewModel() {
    private var daysRepository: DaysRepository = DaysRepository(application)
    val getAllDays: LiveData<List<Days>> = daysRepository.getAllDays()
    val newWeight = MutableLiveData<Double>()
    val latestDay = daysRepository.getLatestDay()

    fun increaseDrink() {
        val latestDay = latestDay.value
        if (latestDay != null && latestDay.drunk < 8) {
            latestDay.drunk += 1
            viewModelScope.launch {
                daysRepository.updateDay(latestDay)
            }
        }
    }

    fun updateWeight(newWeight: Double) {
        viewModelScope.launch {
            val latestDay = latestDay.value
            if(latestDay != null) {
                latestDay.weight = newWeight
                latestDay.currentBMI = calculateBMI(latestDay.weight, latestDay.height)
                daysRepository.updateDay(latestDay)
            }
        }
    }

    private fun calculateBMI(weight: Double?, height: Double?): Double {
        val heightInMeters = height!! / 100.0
        return weight!! / (heightInMeters * heightInMeters)
    }

    fun getTotalCompletedExercise(): LiveData<Int> {
        return daysRepository.getTotalCompletedExercise()
    }

    fun getTotalKcalConsumed(): LiveData<Double> {
        return daysRepository.getTotalKcalConsumed()
    }

    class ReportViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ReportViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReportViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}