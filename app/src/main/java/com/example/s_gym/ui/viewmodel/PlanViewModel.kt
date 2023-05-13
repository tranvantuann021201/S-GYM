package com.example.s_gym.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.ui.adapter.FragmentPlanPagerAdapter
import com.google.android.material.tabs.TabLayout

class PlanViewModel : ViewModel() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: FragmentPlanPagerAdapter

    fun init(tabLayout: TabLayout, viewPager2: ViewPager2, pagerAdapter: FragmentPlanPagerAdapter) {
        this.tabLayout = tabLayout
        this.viewPager2 = viewPager2
        this.pagerAdapter = pagerAdapter
        tabLayout.addTab(tabLayout.newTab().setText("Basic"))
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"))
        viewPager2.adapter = pagerAdapter
    }
}