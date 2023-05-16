package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.api.Exercise
import com.example.s_gym.api.FitnessPlan
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

class AddFitnessViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)

    var exerciseList = listOf<Exercise>()

    fun loadExercises(context: Context) {
        try {
            val jsonString = getJSONFromAssets(context)!!
            val fitnessPlan = Gson().fromJson(jsonString, FitnessPlan::class.java)

            val exerciseSet = mutableSetOf<Exercise>()
            for (day in fitnessPlan.fitnessPlan) {
                for (exercise in day.exercise) {
                    if(day.id != exercise.id) {
                        exerciseSet.add(exercise)
                    }
                }
            }
            exerciseList = exerciseSet.toList().sortedBy { it.id }
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }
    }

    private fun getJSONFromAssets(context: Context): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = context.assets.open("fitness.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    class AddFitnessViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddFitnessViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddFitnessViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}
