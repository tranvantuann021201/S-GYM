package com.example.s_gym.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentReportBinding
import com.example.s_gym.databinding.FragmentRestBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarReport.selectedDate = CalendarDay.today()

        binding.cvHistory.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_historyFragment)
        }

        binding.cvFlDrink.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_followDrinkWaterFragment)
        }

//        binding.calendarReport.addDecorator(object : DayViewDecorator {
//            override fun shouldDecorate(day: CalendarDay): Boolean {
//                val today = CalendarDay.today()
//                return day == today
//            }
//
//            override fun decorate(view: DayViewFacade) {
//                view.setSelectionDrawable(ContextCompat.getDrawable(
//                    requireContext(), R.drawable.day_selector)!!)
//            }
//        })
    }
}