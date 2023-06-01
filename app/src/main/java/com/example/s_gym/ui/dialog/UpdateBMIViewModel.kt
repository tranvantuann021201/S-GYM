package com.example.s_gym.ui.dialog

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.ui.viewmodel.ReportViewModel
import kotlinx.coroutines.launch

class UpdateBMIViewModel(application: Application): ViewModel() {
    private var daysRepository: DaysRepository = DaysRepository(application)
    val latestDay = daysRepository.getLatestDay()

    fun updateLatestDay(weight: Double?, height: Double?) {
        viewModelScope.launch {
            val latestDay = latestDay.value
            if (latestDay != null) {
                if (weight != null) {
                    latestDay.weight = weight
                }
                if (height != null) {
                    latestDay.height = height
                }
                daysRepository.updateDay(latestDay)
            }
        }
    }



    class UpdateBMIViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(UpdateBMIViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UpdateBMIViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}