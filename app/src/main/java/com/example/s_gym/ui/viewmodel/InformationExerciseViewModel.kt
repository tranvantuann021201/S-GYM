package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.api.Exercise
import com.example.s_gym.database.dao.FitnessAdvanceDao
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class InformationExerciseViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    val exerciseAmount = MutableLiveData<Int>()

    fun addExerciseToFitnessAdvance(fitnessAdvanceId: Int, exercises: Exercises) {
        viewModelScope.launch {
            fitnessRepository.addExerciseToFitnessAdvance(fitnessAdvanceId, exercises)
        }
    }

    fun increaseAmount() {
        val currentAmount = exerciseAmount.value ?: 10
        exerciseAmount.value = currentAmount + 5
    }

    fun decreaseAmount() {
        val currentAmount = exerciseAmount.value ?: 10
        if (currentAmount > 5) {
            exerciseAmount.value = currentAmount - 5
        }
    }

    fun convertExerciseToExercises(exercise: Exercise): Exercises {
        return Exercises(
            id = exercise.id,
            name = exercise.name,
            description = exercise.description,
            urlVideoGuide = exercise.urlVideoGuide,
            isComplete = exercise.isComplete,
            kcalCaloriesConsumed = exercise.kcalCaloriesConsumed,
            animationMount = exercise.animationMount
        )
    }

    class InformationExerciseViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(InformationExerciseViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InformationExerciseViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}