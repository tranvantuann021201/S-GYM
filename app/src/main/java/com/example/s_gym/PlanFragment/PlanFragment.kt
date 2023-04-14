package com.example.s_gym.PlanFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentPlanBinding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
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
        pagerAdapter = FragmentPlanPagerAdapter(parentFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Basic"))
        tabLayout.addTab(tabLayout.newTab().setText("Advanced"))

        viewPager2.adapter = pagerAdapter

    }
}