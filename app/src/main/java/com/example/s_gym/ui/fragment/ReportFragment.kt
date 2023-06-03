package com.example.s_gym.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.SingleValueDataSet
import com.anychart.charts.LinearGauge
import com.anychart.enums.*
import com.anychart.scales.OrdinalColor
import com.example.s_gym.R
import com.example.s_gym.database.entity.Days
import com.example.s_gym.databinding.FragmentReportBinding
import com.example.s_gym.ui.dialog.UpdateBMIDialog
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
    private lateinit var timer: CountDownTimer

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

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTotalCompletedExercise().observe(viewLifecycleOwner) { totalCompletedExercise ->
            binding.txtExerAmount.text = totalCompletedExercise.toString()
        }

        viewModel.getTotalKcalConsumed().observe(viewLifecycleOwner) { totalKcalConsumed ->
            binding.txtCaloAmount.text = String.format("%.2f", totalKcalConsumed)
        }

        binding.calendarReport.selectedDate = CalendarDay.today()

        binding.textViewProgress.bringToFront()

        //BMI Chart
        val linearGauge = AnyChart.linear()
        viewModel.latestDay.observe(viewLifecycleOwner) { days ->
            if (days != null) {
                binding.txtWaterDrunk.text = days.drunk.toString()
                binding.drinkProgressBar.progress = days.drunk.toFloat()
                binding.edtWeight.hint = viewModel.latestDay.value?.weight.toString()

                binding.bmiChart.setProgressBar(binding.bmiProgressBar)
                setLinearGauge(linearGauge, days)
                binding.bmiChart.setChart(linearGauge)
                binding.bmiChart.invalidate()
            }
        }

        binding.edtWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

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

        binding.btnEditBmi.setOnClickListener {
            UpdateBMIDialog().show(requireActivity().supportFragmentManager, "updateBMIDialog")
        }

        timer = createCountDownTimer()
        binding.btnDrink.setOnClickListener {
            if (viewModel.latestDay.value?.drunk == 8) {
                Toast.makeText(context, "Hôm nay, bạn đã uống đủ nước.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.increaseDrink()
                Toast.makeText(context, "Hãy thoải mái uống nước  bạn nhé.", Toast.LENGTH_LONG)
                    .show()
                timer.start()
            }
        }


        //Weight Chart
        binding.weightChart.description.isEnabled = false
        binding.weightChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.weightChart.xAxis.valueFormatter = DateValueFormatter()
        binding.weightChart.axisRight.isEnabled = false
        binding.weightChart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureStart(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
            }

            override fun onChartGestureEnd(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {
            }

            override fun onChartLongPressed(me: MotionEvent?) {}
            override fun onChartDoubleTapped(me: MotionEvent?) {}
            override fun onChartSingleTapped(me: MotionEvent?) {}

            override fun onChartFling(
                me1: MotionEvent?,
                me2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ) {
            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {}

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                binding.weightChart.moveViewToX(binding.weightChart.lowestVisibleX + dX)
            }
        }


        var lineData = LineData()
        viewModel.getAllDays.observe(viewLifecycleOwner) { daysData ->
            lineData = LineData(lineChart(daysData))
            binding.weightChart.data = lineData
            binding.weightChart.invalidate()
        }

        binding.txtSupport.setOnClickListener {
            BMISupportDialogFragment().show(requireActivity().supportFragmentManager, "BMISupportDialogFragment")
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

    private fun createCountDownTimer(): CountDownTimer {
        var timeLeft = 6000L // Thời gian ban đầu của bộ đếm ngược
        return object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(p0: Long) {
                timeLeft = p0
                val minutes = (p0 / 1000) / 60
                val seconds = (p0 / 1000) % 60
                binding.btnDrink.isEnabled = false
                binding.btnDrink.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.btnDrink.isEnabled = true
                binding.btnDrink.text = "Uống"
            }
        }
    }

    private fun setLinearGauge(linearGauge: LinearGauge, days: Days): LinearGauge {
        linearGauge.data(SingleValueDataSet(arrayOf(days.currentBMI)))

        linearGauge.layout(Layout.HORIZONTAL)
        val scaleBarColorScale = OrdinalColor.instantiate()
        scaleBarColorScale.ranges(
            arrayOf(
                "{ from: 0, to: 18, color: ['red 0.5'] }",
                "{ from: 18, to: 25, color: ['green 0.5'] }",
                "{ from: 25, to: 30, color: ['yellow 0.5'] }",
                "{ from: 30, to: 40, color: ['orange 0.5'] }",
                "{ from: 40, to: 100, color: ['red 0.5'] }"
            )
        )

        linearGauge.scaleBar(0)
            .width("10%")
            .colorScale(scaleBarColorScale)

        linearGauge.marker(0)
            .type(MarkerType.TRIANGLE_DOWN)
            .color("red")
            .offset("-3.5%")
            .zIndex(20)

        linearGauge.scale()
            .minimum(0)
            .maximum(50)

        linearGauge.axis(0)
            .minorTicks(false)
            .width("-2%")
        linearGauge.axis(0)
            .offset("2%")
            .orientation(Orientation.TOP)
            .labels("top")

        return linearGauge
    }
}
