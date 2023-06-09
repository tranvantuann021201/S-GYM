package com.example.s_gym.ui.viewmodel

import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.repository.SettingRepository
import androidx.lifecycle.viewModelScope
import com.example.s_gym.MainActivity
import com.example.s_gym.until.NotifyRemindDrinkBR
import com.example.s_gym.until.NotifyRemindFitnessBR
import kotlinx.coroutines.launch
import java.util.*

class RemindViewModel(application: Application): ViewModel() {
    private var settingRepository: SettingRepository = SettingRepository(application)
    val currentUser = MainActivity.currentFirebaseUser
    val reference = MainActivity.firebaseDatabase.reference

    fun getSetting(userId: String): LiveData<Setting> {
        return settingRepository.getSettingsByUserId(userId)
    }

    fun getAllSettings(): List<Setting> {
        return settingRepository.getAllSettings()
    }

    fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            settingRepository.update(setting)
            reference.child("Setting").child(currentUser!!.uid).setValue(setting)
        }
    }

    fun bookRemindWaterNotify(context: Context, activity: Activity) {
        // Đặt lịch 8 thông báo khác nhau
        val times = arrayOf(7, 9, 11, 13, 15, 17, 19, 21)
        for (hour in times) {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, 0)
            }

            // Kiểm tra xem thời gian đặt cho thông báo có quá sớm không
            if (calendar.timeInMillis < System.currentTimeMillis()) {
                // Thời gian đặt cho thông báo đã qua
                // Đẩy lùi thông báo sang ngày hôm sau
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            val intent = Intent(context, NotifyRemindDrinkBR::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                hour,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager =
                activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val interval = AlarmManager.INTERVAL_DAY
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                interval,
                pendingIntent
            )
        }
    }

    fun cancelRemindWaterNotify(context: Context, activity: Activity) {
        // Hủy bỏ tất cả 8 thông báo
        val times = arrayOf(7, 9, 11, 13, 15, 17, 19, 21)
        for (hour in times) {
            val intent = Intent(context, NotifyRemindDrinkBR::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                hour,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager =
                activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }

    fun bookRemindFitnessNotify(context: Context, activity: Activity, notifyTime: String) {
        val timeParts = notifyTime.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        // Đặt lịch thông báo
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            // Thời gian đặt cho thông báo đã qua
            // Đẩy lùi thông báo sang ngày hôm sau
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val intent = Intent(context, NotifyRemindFitnessBR::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Đặt lịch thông báo lặp lại hàng ngày
        val interval = AlarmManager.INTERVAL_DAY
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            interval,
            pendingIntent
        )
    }

    fun cancelRemindFitnessNotify(context: Context, activity: Activity) {
        // Hủy bỏ thông báo
        val intent = Intent(context, NotifyRemindFitnessBR::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager =
            activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    class RemindViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(RemindViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RemindViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}