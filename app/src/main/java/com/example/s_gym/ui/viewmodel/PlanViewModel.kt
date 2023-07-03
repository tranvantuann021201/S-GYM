package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.SettingRepository
import com.example.s_gym.ui.adapter.FragmentPlanPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PlanViewModel(application: Application) : ViewModel() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: FragmentPlanPagerAdapter
    private var daysRepository: DaysRepository = DaysRepository(application)
    private var daysListDefault = mutableListOf<Days>()
    var currentUser = MainActivity.currentFirebaseUser
    var reference = MainActivity.firebaseDatabase.reference

    fun latestDays(): LiveData<Days> {
        return if (currentUser != null) {
            daysRepository.getLatestDay(currentUser!!.uid)
        } else {
            daysRepository.getLatestDay("default")
        }
    }

    fun deletedDayWhereId(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            daysRepository.deletedDayWhereId(id)
        }
    }

    fun deleteAllDays() {
        CoroutineScope(Dispatchers.IO).launch {
            daysRepository.deleteAllFromDays()
        }
    }

    fun getDayListDefault(): List<Days> {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = dateFormat.format(currentDate)
        if (currentUser != null) {
            daysListDefault.add(Days(0,
                "26-06-2023",
                0,
                10,
                3,
                55.5,
                170.0,
                120.2,
                19.1,
                currentUser!!.uid))
            daysListDefault.add(Days(1, "27-06-2023", 0, 10, 3, 55.6, 170.0, 120.2, 19.1, currentUser!!.uid))
            daysListDefault.add(Days(2, "28-06-2023", 0, 10, 3, 55.5, 170.0, 120.2, 19.1, currentUser!!.uid))
            daysListDefault.add(Days(3, "29-06-2023", 0, 10, 3, 55.4, 170.0, 120.2, 19.1, currentUser!!.uid))
            daysListDefault.add(Days(4, dateString, 0, 10, 3, 55.5, 170.0, 120.2, 19.1, currentUser!!.uid))

            for(days in daysListDefault) {
//                CoroutineScope(Dispatchers.IO).launch {
//                    daysRepository.insertDay(days)
//                }
                reference.child("Days").child(currentUser!!.uid).child(days.name).setValue(days)
            }
        }

        return daysListDefault
    }

    fun init(tabLayout: TabLayout, viewPager2: ViewPager2, pagerAdapter: FragmentPlanPagerAdapter) {
        this.tabLayout = tabLayout
        this.viewPager2 = viewPager2
        this.pagerAdapter = pagerAdapter
        tabLayout.addTab(tabLayout.newTab().setText("Cơ bản"))
        tabLayout.addTab(tabLayout.newTab().setText("Nâng cao"))
        viewPager2.adapter = pagerAdapter
    }

    class PlanViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PlanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlanViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
    interface OnCurrentUserChangedListener {
        fun onCurrentUserChanged(user: FirebaseUser?)
    }

}