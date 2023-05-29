package com.example.s_gym

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.s_gym.database.AppDatabase
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.databinding.ActivityMainBinding
import com.example.s_gym.ui.fragment.PlanFragment
import com.example.s_gym.ui.fragment.ReportFragment
import com.example.s_gym.ui.fragment.SettingFragment
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel
import com.example.s_gym.until.DailyWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var  navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView : BottomNavigationView
    val daysRepository by lazy { DaysRepository(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: auto generate new Days object with WorkManager
        scheduleDailyWorker()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment

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
//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true)
//            .build()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
//            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "InsertNewDaysObject",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 11)
            if ((get(Calendar.HOUR_OF_DAY) == 23 && get(Calendar.MINUTE) >= 10)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return next.timeInMillis - now.timeInMillis
    }

}