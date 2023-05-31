package com.example.s_gym.ui.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.database.entity.Days
import com.example.s_gym.databinding.FragmentReportBinding
import com.example.s_gym.ui.viewmodel.ReportViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModelFactory: ReportViewModel.ReportViewModelFactory
    private lateinit var viewModel: ReportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = ReportViewModel.ReportViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ReportViewModel::class.java]

//        viewModel.insertFakeDaysData()
    }

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

        viewModel.latestDay.observe(viewLifecycleOwner) { days ->
            if (days != null) {
                binding.edtWeight.setText(days.weight.toString())
            }
        }

        binding.edtWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newWeight = s.toString().toDoubleOrNull()
                viewModel.newWeight.value = newWeight
            }
        })

        viewModel.newWeight.observe(viewLifecycleOwner) { newWeight ->
            if (newWeight != null) {
                viewModel.updateWeight(newWeight)
            }
        }

        binding.cvHistory.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_historyFragment)
        }

        binding.cvFlDrink.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_followDrinkWaterFragment)
        }

        binding.btnEditBmi.setOnClickListener {
            findNavController().navigate(R.id.action_reportFragment_to_updateBMIDialog)
        }

        binding.weightChart.description.isEnabled = false
        binding.weightChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.weightChart.xAxis.valueFormatter = DateValueFormatter()
        binding.weightChart.axisRight.isEnabled = false
        binding.weightChart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureStart(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {}

            override fun onChartGestureEnd(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {}

            override fun onChartLongPressed(me: MotionEvent?) {}

            override fun onChartDoubleTapped(me: MotionEvent?) {}

            override fun onChartSingleTapped(me: MotionEvent?) {}

            override fun onChartFling(
                me1: MotionEvent?,
                me2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ) {}

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {}

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                binding.weightChart.moveViewToX( binding.weightChart.lowestVisibleX + dX)
            }
            // Các phương thức khác
        }

        viewModel.getAllDays.observe(viewLifecycleOwner) { daysData ->
            val lineData = LineData(lineChart(daysData))
            binding.weightChart.data = lineData
            binding.weightChart.invalidate()
        }
    }

    class DateValueFormatter : ValueFormatter() {
        private val dateFormat = SimpleDateFormat("dd MMM", Locale.forLanguageTag("vi-VN"))
        override fun getFormattedValue(value: Float): String {
            val date = Date(value.toLong())
            return dateFormat.format(date)
        }
    }

    private fun lineChart(daysData: List<Days>): LineDataSet {
        // Tạo một danh sách DataEntry để lưu trữ dữ liệu cho biểu đồ
        val dataEntries = mutableListOf<Entry>()
        for (day in daysData) {
            // Lấy giá trị cân nặng từ trường "weight" của đối tượng "Days"
            val x = convertDateToFloat(day.name)
            val y = day.weight.toFloat()
            // Tạo một đối tượng Entry mới với giá trị x và y tương ứng
            val dataEntry = Entry(x, y)
            // Thêm đối tượng ValueDataEntry vào danh sách DataEntry
            dataEntries.add(dataEntry)
        }

        val dataSet = LineDataSet(dataEntries, "Weight")
        dataSet.setDrawFilled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dataSet.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_blue)
        } else {
            dataSet.fillColor = Color.BLUE
        }


        return dataSet
    }

    private fun convertDateToFloat(dateString: String): Float {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return date.time.toFloat()
    }

}
