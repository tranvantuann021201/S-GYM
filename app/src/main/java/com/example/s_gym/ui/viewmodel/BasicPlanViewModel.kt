package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.api.FitnessDay
import com.example.s_gym.api.FitnessPlan
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.repository.FitnessBasicRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.util.*

class BasicPlanViewModel(application: Application) : ViewModel() {
    private val calendar = Calendar.getInstance()
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)
    private val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    private val fitnessBasicRepository = FitnessBasicRepository(application)
    val allBasic: LiveData<List<FitnessBasic>> = fitnessBasicRepository.allFitnessBasics


    @RequiresApi(Build.VERSION_CODES.O)
    val daysInRange = (1..daysInMonth).map { day ->
        LocalDateTime.of(year, month + 1, day, 0, 0, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFitnessBasicFlowMonth(fitnessBasic: List<FitnessBasic>): List<FitnessBasic> {
        return try {
            fitnessBasic.filter { fitnessDay ->
                daysInRange.any { day ->
                    fitnessDay.id == day.dayOfMonth
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFitnessDays(context: Context): List<FitnessDay> {
        return try {
            val jsonString = getJSONFromAssets(context)!!
            val fitnessPlan = Gson().fromJson(jsonString, FitnessPlan::class.java)

            val fitnessDays = fitnessPlan.fitnessPlan
            fitnessDays.filter { fitnessDay ->
                daysInRange.any { day ->
                    fitnessDay.id == day.dayOfMonth
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            emptyList()
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

    private fun fitnessDayToBasic(fitnessDay: FitnessDay): FitnessBasic {
        return FitnessBasic(
            id = fitnessDay.id,
            nameDay = fitnessDay.nameDay,
            isRestDay = fitnessDay.isRestDay,
            totalExercise = fitnessDay.totalExercise,
            exerciseCompleted = fitnessDay.exerciseCompleted,
            exercise = fitnessDay.exercise.map { exercise ->
                Exercises(
                    id = exercise.id,
                    name = exercise.name,
                    description = exercise.description,
                    urlVideoGuide = exercise.urlVideoGuide,
                    isComplete = exercise.isComplete,
                    kcalCaloriesConsumed = exercise.kcalCaloriesConsumed,
                    animationMount = exercise.animationMount
                )
            }
        )
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            fitnessBasicRepository.deleteAll()
        }
    }

    fun copyFitnessDayToBasic(fitnessDay: FitnessDay) {
        val fitnessBasicMode = fitnessDayToBasic(fitnessDay)
        CoroutineScope(Dispatchers.IO).launch {
            fitnessBasicRepository.insert(fitnessBasicMode)
        }
    }

    class BasicPlanViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BasicPlanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BasicPlanViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }

}