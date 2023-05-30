package com.example.s_gym.until

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.s_gym.MainActivity
import com.example.s_gym.database.repository.DaysRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DailyWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val daysRepository by lazy { DaysRepository(context) }

    override suspend fun doWork(): Result {
        Log.d("DailyWorker", "doWork: started")
        return try {
            daysRepository.addNewDay()
            Log.d("DailyWorker", "doWork: success")
            Result.success()
        } catch (e: Exception) {
            Log.e("DailyWorker", "doWork: failed", e)
            Result.failure()
        }
    }
}
