package com.example.s_gym.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.s_gym.ui.fragment.AdvancedPlanFragment
import com.example.s_gym.ui.fragment.BasicPlanFragment

class FragmentPlanPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            BasicPlanFragment()
        } else
            AdvancedPlanFragment()
    }
}