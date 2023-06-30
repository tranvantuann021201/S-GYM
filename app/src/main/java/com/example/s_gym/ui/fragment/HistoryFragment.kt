package com.example.s_gym.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.database.entity.Days
import com.example.s_gym.databinding.FragmentHistoryBinding
import com.example.s_gym.ui.adapter.HistoryAdapter
import com.example.s_gym.ui.viewmodel.HistoryViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModelFactory: HistoryViewModel.HistoryViewModelFactory
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = HistoryViewModel.HistoryViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendar.selectedDate = CalendarDay.today()

        historyAdapter = HistoryAdapter(emptyList())

        viewModel.getAllDays.observe(viewLifecycleOwner) { daysList ->
            val dayListShow = mutableListOf<Days>()
            for (days in daysList) {
                if (days.completedExercise != 0) {
                    dayListShow.add(days)
                }
            }
            historyAdapter = HistoryAdapter(dayListShow)
            binding.rvHistoryList.adapter = historyAdapter
            binding.rvHistoryList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_reportFragment)
        }
    }
}