package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.s_gym.api.Exercise
import com.example.s_gym.api.FitnessPlan
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import com.example.s_gym.database.repository.FitnessBasicRepository
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

class AddFitnessViewModel(application: Application): ViewModel() {
    private var fitnessRepository: FitnessAdvanceRepository = FitnessAdvanceRepository(application)
    private var fitnessBasicRepository: FitnessBasicRepository = FitnessBasicRepository(application)
    var allBasic = fitnessBasicRepository.allFitnessBasics
    var exercisesList: MutableList<Exercises> = mutableListOf()

    var exerciseListJSON = listOf<Exercise>()

    fun getAllExercise(allBasics: List<FitnessBasic>): MutableList<Exercises> {
        val exercises = allBasics.flatMap { it.exercise }.distinctBy { it.id }
        exercisesList = exercises.toMutableList()
        return exercisesList
    }

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
            exerciseListJSON = exerciseSet.toList().sortedBy { it.id }
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
