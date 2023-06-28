package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.work.*
import com.example.s_gym.MainActivity
import com.example.s_gym.ui.adapter.FragmentPlanPagerAdapter
import com.example.s_gym.R
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.databinding.FragmentPlanBinding
import com.example.s_gym.ui.viewmodel.PlanViewModel
import com.example.s_gym.until.DailyWorker
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [PlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanFragment : Fragment() {
    private lateinit var binding: FragmentPlanBinding
    private lateinit var viewModelFactory: PlanViewModel.PlanViewModelFactory
    private lateinit var viewModel: PlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = PlanViewModel.PlanViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlanViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleDailyWorker()

        val pagerAdapter = FragmentPlanPagerAdapter(childFragmentManager, lifecycle)
        viewModel.init(binding.tabLayout, binding.viewPager2, pagerAdapter)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.tabLayout.setTabTextColors(
            resources.getColor(R.color.black),
            resources.getColor(R.color.white)
        )

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    private fun scheduleDailyWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()

//        WorkManager.getInstance(requireContext()).cancelAllWork()

        viewModel.latestDays.observe(viewLifecycleOwner){ latestDays ->
            val daysData = workDataOf("days" to Gson().toJson(latestDays))

            val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .setInputData(daysData)
                .build()

            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                "dailyWork",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWorkRequest
            )
        }
    }

    private fun calculateInitialDelay(): Long {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
            if (before(now)) add(Calendar.DAY_OF_MONTH, 1)
        }
        val initialDelay = next.timeInMillis - now.timeInMillis
        val hours = initialDelay / 1000 / 3600
        val minutes = initialDelay / 1000 / 60 % 60
        val seconds = initialDelay / 1000 % 60
        val calculateInitialDelay = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        Log.e("calculateInitialDelay", "calculateInitialDelay: $calculateInitialDelay")
        //last id = 64, has yet removed
        return initialDelay
    }
}
