package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.icu.text.Transliterator.Position
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.FitnessBasicRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class FitnessViewModel(application: Application) : ViewModel() {
    private val fitnessBasicRepository = FitnessBasicRepository(application)
    var daysRepository = DaysRepository(application)
    val latestDay = daysRepository.getLatestDay()
    var fitnessBasic = MutableLiveData<FitnessBasic>()
    var currentUser = MainActivity.currentFirebaseUser
    var reference = MainActivity.firebaseDatabase.reference
    fun onBtnDoneClick(exercises: Exercises, fitnessBasic: FitnessBasic?) {
        updateCompletedExercise()
        updateKcalConsumed(exercises.kcalCaloriesConsumed)
        if (fitnessBasic != null) {
            updateExerciseCompleted(fitnessBasic)
        }
    }

    private fun updateExerciseCompleted(fitnessBasic: FitnessBasic) {
        viewModelScope.launch {
            fitnessBasic.exerciseCompleted += 1
            fitnessBasicRepository.updateFitnessBasic(fitnessBasic)
            reference.child("FitnessBasic").child(currentUser!!.uid).child(fitnessBasic.id.toString()).get()
                .addOnSuccessListener {
                    val updates = mapOf(
                        "exerciseCompleted" to fitnessBasic.exerciseCompleted
                    )
                    reference.child("FitnessBasic").child(currentUser!!.uid)
                        .child(fitnessBasic.id.toString()).updateChildren(updates)
                }
        }
    }

    private fun updateCompletedExercise() {
        val latestDay = latestDay.value
        viewModelScope.launch {
            latestDay?.let {
                it.completedExercise += 1
                daysRepository.updateDay(it)
            }
        }
    }


    private fun updateKcalConsumed(kcalCaloriesConsumed: Double) {
        val latestDay = latestDay.value
        viewModelScope.launch {
            latestDay?.let {
                it.kcalConsumed += kcalCaloriesConsumed
                daysRepository.updateDay(it)
            }
        }
    }


    class FitnessViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FitnessViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FitnessViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }

}