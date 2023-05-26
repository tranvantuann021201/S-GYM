package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch

class NameMyExercisesViewModel(application: Application): ViewModel() {
    private val _fitnessAdvanceName: MutableLiveData<String> = MutableLiveData()
    val fitnessAdvanceName: MutableLiveData<String> = _fitnessAdvanceName
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    private val _rowCount = MutableLiveData<Int>()
    val rowCount: LiveData<Int> = _rowCount

    fun getRowCountFitnessAdvanceList() {
        viewModelScope.launch {
            val count = fitnessRepository.getRowCount()
            _rowCount.value = count
        }
    }
    fun updateFitnessAdvanceName(id: Int, newName: String) {
        viewModelScope.launch {
            fitnessRepository.updateFitnessAdvanceName(id, newName)
            _fitnessAdvanceName.value = newName
        }
    }

    class NameMyExercisesViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NameMyExercisesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NameMyExercisesViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}