package com.example.s_gym.ui.viewmodel

import android.app.Application
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.database.repository.SettingRepository
import com.example.s_gym.ui.adapter.FragmentPlanPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : ViewModel() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: FragmentPlanPagerAdapter
    private var daysRepository: DaysRepository = DaysRepository(application)
    val yesterday = daysRepository.getYesterday()
    val latestDays = daysRepository.getLatestDay()
    private var settingRepository: SettingRepository = SettingRepository(application)

    fun insert(setting: Setting) {
        viewModelScope.launch {
            settingRepository.insert(setting)
        }
    }

    fun getSetting(userId: String): LiveData<Setting> {
        return settingRepository.getSettingsByUserId(userId)
    }

    fun deletedDayByIDOption(){
        viewModelScope.launch {
            daysRepository.deletedDayByIDOption()
        }
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
}