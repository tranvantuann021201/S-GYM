package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import kotlinx.coroutines.launch

class NameMyExercisesViewModel(application: Application): ViewModel() {
    private val _fitnessAdvanceName: MutableLiveData<String> = MutableLiveData()
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    var currentUser = MainActivity.currentFirebaseUser
    var reference = MainActivity.firebaseDatabase.reference
    val fitnessAdvanceList: LiveData<List<FitnessAdvance>> = fitnessRepository.readAllData(currentUser!!.uid)

    fun updateFitnessAdvanceName(id: Int, newName: String) {
        viewModelScope.launch {
            fitnessRepository.updateFitnessAdvanceName(id, newName)
            reference.child("FitnessAdvance").child(currentUser!!.uid).child(id.toString()).get()
                .addOnSuccessListener{
                    val updates = mapOf(
                        "name" to newName
                    )
                    reference.child("FitnessAdvance").child(currentUser!!.uid).
                    child(id.toString()).updateChildren(updates)
                }
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