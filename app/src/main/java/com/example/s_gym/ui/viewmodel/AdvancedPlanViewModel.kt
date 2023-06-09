package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdvancedPlanViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    private var daysRepository: DaysRepository = DaysRepository(application)
    var currentUser = MainActivity.currentFirebaseUser
    var reference = MainActivity.firebaseDatabase.reference


    suspend fun readAllData(userId: String): LiveData<List<FitnessAdvance>> {
        return fitnessRepository.readAllData(userId)
    }

    suspend fun addFitnessAdvance(fitnessAdvance: FitnessAdvance): Long {
        return withContext(viewModelScope.coroutineContext) {
            fitnessRepository.addFitnessAdvance(fitnessAdvance)
        }
    }

    fun addFitnessAdvanceToCloud(fitnessAdvanceId: Long) {
        var fitness: FitnessAdvance? = null
        CoroutineScope(Dispatchers.IO).launch {
            fitness = fitnessRepository.getFitnessAdvanceById(fitnessAdvanceId.toInt())
        }
        reference.child("FitnessAdvance").child(currentUser!!.uid).get()
            .addOnSuccessListener{
                reference.child("FitnessAdvance").child(currentUser!!.uid).
                child(fitnessAdvanceId.toString()).setValue(fitness)
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
            reference.child("FitnessAdvance").child(currentUser!!.uid).get()
                .addOnSuccessListener{
                    reference.child("FitnessAdvance").child(currentUser!!.uid).
                    child(fitnessAdvance.id.toString()).removeValue()
                }
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