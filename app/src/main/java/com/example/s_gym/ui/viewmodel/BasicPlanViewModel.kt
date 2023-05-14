package com.example.s_gym.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.s_gym.api.FitnessDay
import com.example.s_gym.api.FitnessPlan
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.util.*

class BasicPlanViewModel : ViewModel() {
    private val calendar = Calendar.getInstance()
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)
    private val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    @RequiresApi(Build.VERSION_CODES.O)
    private val daysInRange = (1..daysInMonth).map { day ->
        LocalDateTime.of(year, month + 1, day, 0, 0, 0)
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
}