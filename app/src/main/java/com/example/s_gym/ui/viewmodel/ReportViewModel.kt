package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.api.FitnessDay
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.FitnessBasicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(application: Application): ViewModel() {
    private var daysRepository: DaysRepository = DaysRepository(application)

    val getAllDays: LiveData<List<Days>> = daysRepository.getAllDays()
    val newWeight = MutableLiveData<Double>()
    val latestDay = daysRepository.getLatestDay()

    fun insertFakeDaysData() {
        viewModelScope.launch {
            daysRepository.insertDay(Days(0, "2022-11-01", 3, 3, 3, 50.0, 175.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-02", 3, 3, 3, 50.0, 174.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-04", 3, 3, 3, 51.0, 173.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-06", 3, 3, 3, 51.0, 174.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-08", 3, 3, 3, 55.0, 175.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-09", 3, 3, 3, 52.0, 176.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-10", 3, 3, 3, 53.0, 177.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-11", 3, 3, 3, 56.0, 176.0, 20.4, 0.0))
            daysRepository.insertDay(Days(0, "2022-11-12", 3, 3, 3, 51.0, 175.0, 20.4, 0.0))

        }
    }

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
            daysRepository.updateWeight(newWeight)
        }
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