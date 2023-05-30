package com.example.s_gym

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.databinding.ActivityMainBinding
import com.example.s_gym.ui.fragment.PlanFragment
import com.example.s_gym.ui.fragment.ReportFragment
import com.example.s_gym.ui.fragment.SettingFragment
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel
import com.example.s_gym.until.DailyWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        //TODO: auto generate new Days object with WorkManager
        scheduleDailyWorker()

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
    }

    //TODO: auto generate new Days object with WorkManager
    private fun scheduleDailyWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()

//        WorkManager.getInstance(this).cancelAllWork()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "dailyWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }
        val initialDelay = next.timeInMillis - now.timeInMillis
        val hours = initialDelay / 1000 / 3600
        val minutes = initialDelay / 1000 / 60 % 60
        val seconds = initialDelay / 1000 % 60
        val calculateInitialDelay = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.e("calculateInitialDelay", "calculateInitialDelay: $calculateInitialDelay")
        //last id = 64, has yet removed
        return initialDelay
    }
}