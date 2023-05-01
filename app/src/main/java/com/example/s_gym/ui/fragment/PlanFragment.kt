package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.adapter.FragmentPlanPagerAdapter
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentPlanBinding
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 * Use the [PlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmentPlanBinding
    private lateinit var pagerAdapter: FragmentPlanPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position



                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        tabLayout.setTabTextColors(
            resources.getColor(R.color.black),
            resources.getColor(R.color.white))

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    private fun init() {
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2
        pagerAdapter = FragmentPlanPagerAdapter(childFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Basic"))
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"))

        viewPager2.adapter = pagerAdapter

    }
}