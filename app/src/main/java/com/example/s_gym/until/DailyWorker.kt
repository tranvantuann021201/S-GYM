package com.example.s_gym.until

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository
import com.google.gson.Gson

class DailyWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val daysRepository by lazy { DaysRepository(context) }
    private val daysJson = inputData.getString("days")
    private val latestDay = Gson().fromJson(daysJson, Days::class.java)
    override suspend fun doWork(): Result {
        Log.d("DailyWorker", "doWork: started")
        return try {
            daysRepository.addNewDay(latestDay)
            Log.d("DailyWorker", "doWork: success")
            Result.success()
        } catch (e: Exception) {
            Log.e("DailyWorker", "doWork: failed", e)
            Result.failure()
        }
    }
}
