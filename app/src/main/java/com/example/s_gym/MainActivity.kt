package com.example.s_gym

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.databinding.ActivityMainBinding
import com.example.s_gym.ui.fragment.PlanFragment
import com.example.s_gym.ui.fragment.ReportFragment
import com.example.s_gym.ui.fragment.SettingFragment
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel
import com.example.s_gym.until.DailyWorker
import com.example.s_gym.until.NotifyBroadcastReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.coroutineScope
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment

        navController = navHostFragment.findNavController()
        navController.setGraph(R.navigation.nav_graph)
        bottomNavigationView = binding.bottomNav

        // Điều hướng đến HomeFragment
        navController.navigate(R.id.planFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.plan -> {
                    navController.popBackStack(R.id.planFragment, false)
                    navController.navigate(R.id.planFragment)
                    Toast.makeText(this, "PlanFragment", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.report -> {
                    navController.popBackStack(R.id.reportFragment, false)
                    navController.navigate(R.id.reportFragment)
                    true
                }
                R.id.settings -> {
                    navController.popBackStack(R.id.settingFragment, false)
                    navController.navigate(R.id.settingFragment)
                    true
                }
                else -> {
                    Toast.makeText(this, "PlanFragment", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.planFragment, R.id.reportFragment, R.id.settingFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

        notifyManagerRegister()

        // Lấy giá trị của trạng thái thông báo từ SharedPreferences
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val isNotificationScheduled = sharedPref.getBoolean("isNotificationScheduled", false)

        // Nếu thông báo chưa được đặt lịch thì đặt lịch thông báo và cập nhật trạng thái thông báo trong SharedPreferences
        if (!isNotificationScheduled) {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 16)
                set(Calendar.MINUTE, 30)
            }

            val intent = Intent(this, NotifyBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

            with (sharedPref.edit()) {
                putBoolean("isNotificationScheduled", true)
                apply()
            }

//            alarmManager.cancel(pendingIntent)
        }
    }

    private fun notifyManagerRegister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL_ID_A"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        var firebaseDatabase = FirebaseDatabase.getInstance()
    }
}