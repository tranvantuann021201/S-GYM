package com.example.s_gym

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

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

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                currentFirebaseUser = firebaseUser
            } else {
                // Người dùng đã đăng xuất
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener)

        notifyManagerRegister()
    }

    private fun notifyManagerRegister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Tạo kênh thông báo cho swExerRemind
            val name = "Nhắc nhở luyện tập"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("FITNESS_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            // Tạo kênh thông báo cho swDrinkWaterRemind
            val waterName = "Nhắc nhở uống nước"
            val waterDescriptionText = "waterDescriptionText"
            val waterChannel =
                NotificationChannel("WATER_CHANNEL_ID", waterName, importance).apply {
                    description = waterDescriptionText
                }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(waterChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener)
    }

    companion object {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var currentFirebaseUser: FirebaseUser? = null
    }
}