package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.database.repository.DaysRepository
import com.example.s_gym.ui.adapter.FragmentPlanPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class PlanViewModel(application: Application) : ViewModel() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: FragmentPlanPagerAdapter
    private var daysRepository: DaysRepository = DaysRepository(application)
    val latestDay = daysRepository.getLatestDay()

    fun deletedDayByIDOption(){
        viewModelScope.launch {
            daysRepository.deletedDayByIDOption()
        }
    }

    fun init(tabLayout: TabLayout, viewPager2: ViewPager2, pagerAdapter: FragmentPlanPagerAdapter) {
        this.tabLayout = tabLayout
        this.viewPager2 = viewPager2
        this.pagerAdapter = pagerAdapter
        tabLayout.addTab(tabLayout.newTab().setText("Basic"))
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"))
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