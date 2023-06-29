package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch
import kotlin.math.log

class NewFitnessViewModel(application: Application) : ViewModel() {
    val exercisesList = MutableLiveData<List<Exercises>>()
    val fitnessAdvanceLiveData = MutableLiveData<FitnessAdvance>()
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    var currentUser = MainActivity.currentFirebaseUser
    var reference = MainActivity.firebaseDatabase.reference

    fun updateExercisesList(newExercisesList: List<Exercises>, fitnessAdvanceId: Int) {
        val fitnessAdvance = fitnessAdvanceLiveData.value ?: return
        if (fitnessAdvance.id == fitnessAdvanceId) {
            fitnessAdvance.exercisesList = newExercisesList
            fitnessAdvanceLiveData.value = fitnessAdvance
            Log.e("=========", "updateExercisesList: ${fitnessAdvance.exercisesList.size}")
            viewModelScope.launch {
                Log.e("=========", "updateExercisesList: ${fitnessAdvance.exercisesList.size}")
                fitnessRepository.updateFitnessAdvance(fitnessAdvance)
            }
            updateExerciseListOnCloud(newExercisesList, fitnessAdvanceId)
        }
    }

    fun reorderExercisesList(newExercisesList: List<Exercises>, fitnessAdvanceId: Int) {
        val fitnessAdvance = fitnessAdvanceLiveData.value ?: return
        if (fitnessAdvance.id == fitnessAdvanceId) {
            fitnessAdvance.exercisesList = newExercisesList.toMutableList()
            fitnessAdvanceLiveData.value = fitnessAdvance
            viewModelScope.launch {
                fitnessRepository.updateFitnessAdvance(fitnessAdvance)
            }
            updateExerciseListOnCloud(newExercisesList, fitnessAdvanceId)
        }
    }

    fun removeExerciseFromList(exercise: Exercises, fitnessAdvanceId: Int) {
        val fitnessAdvance = fitnessAdvanceLiveData.value ?: return
        if (fitnessAdvance.id == fitnessAdvanceId) {
            val mutableList = fitnessAdvance.exercisesList.toMutableList()
            mutableList.remove(exercise)
            fitnessAdvance.exercisesList = mutableList
            fitnessAdvanceLiveData.value = fitnessAdvance
            viewModelScope.launch {
                fitnessRepository.updateFitnessAdvance(fitnessAdvance)
            }
            updateExerciseListOnCloud(mutableList, fitnessAdvanceId)
        }
    }

    private fun updateExerciseListOnCloud(newExercisesList: List<Exercises>, fitnessAdvanceId: Int) {
        reference.child("FitnessAdvance").child(currentUser!!.uid)
            .child(fitnessAdvanceId.toString()).get()
            .addOnSuccessListener{
                val updates = mapOf(
                    "exerciseList" to newExercisesList
                )
                reference.child("FitnessAdvance").child(currentUser!!.uid).
                child(fitnessAdvanceId.toString()).updateChildren(updates)
            }
    }
    class NewFitnessViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewFitnessViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewFitnessViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}