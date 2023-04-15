package com.example.s_gym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.s_gym.ui.fragment.HistoryFragment
import com.example.s_gym.ui.fragment.PlanFragment
import com.example.s_gym.ui.fragment.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(PlanFragment())
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(PlanFragment())
                    true
                }
                R.id.message -> {
                    loadFragment(ReportFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(HistoryFragment())
                    true
                }
                else -> {loadFragment(PlanFragment())
                    true}
            }
        }
    }
    private  fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}