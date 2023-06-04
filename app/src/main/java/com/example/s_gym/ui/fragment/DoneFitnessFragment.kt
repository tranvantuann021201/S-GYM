package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.SingleValueDataSet
import com.anychart.charts.LinearGauge
import com.anychart.enums.Layout
import com.anychart.enums.MarkerType
import com.anychart.enums.Orientation
import com.anychart.scales.OrdinalColor
import com.example.s_gym.R
import com.example.s_gym.database.entity.Days
import com.example.s_gym.databinding.FragmentDoneFitnessBinding
import com.example.s_gym.ui.dialog.UpdateBMIDialog
import com.example.s_gym.ui.viewmodel.DoneFitnessViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFitnessFragment : Fragment() {
    private lateinit var binding: FragmentDoneFitnessBinding
    private lateinit var viewModelFactory: DoneFitnessViewModel.DoneFitnessViewModelFactory
    private lateinit var viewModel: DoneFitnessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DoneFitnessViewModel.DoneFitnessViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DoneFitnessViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoneFitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Tạo một OnBackPressedCallback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Chuyển hướng sang Fragment C khi nhấn nút Back button
                findNavController().navigate(R.id.action_doneFitnessFragment_to_historyFragment)
            }
        }
        // Đăng ký OnBackPressedCallback
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_doneFitnessFragment_to_historyFragment)
        }

        viewModel.getTotalCompletedExercise().observe(viewLifecycleOwner) { totalCompletedExercise ->
            binding.txtExerAmount.text = totalCompletedExercise.toString()
        }

        viewModel.getTotalKcalConsumed().observe(viewLifecycleOwner) { totalKcalConsumed ->
            binding.txtCaloAmount.text = String.format("%.2f", totalKcalConsumed)
        }

        val linearGauge = AnyChart.linear()
        viewModel.latestDay.observe(viewLifecycleOwner) { days ->
            if (days != null) {
                binding.edtWeight.hint = viewModel.latestDay.value?.weight.toString()

                binding.bmiChart.setChart(linearGauge)
                setLinearGauge(linearGauge, days)
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

        binding.btnEditBmi.setOnClickListener {
            val updateBMIDialog = UpdateBMIDialog()
            updateBMIDialog.show(requireActivity().supportFragmentManager, "updateBMIDialog")
//            findNavController().navigate(R.id.action_reportFragment_to_updateBMIDialog)
        }

        binding.txtSupport.setOnClickListener {
            BMISupportDialogFragment().show(requireActivity().supportFragmentManager, "BMISupportDialogFragment")
        }

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_doneFitnessFragment_to_historyFragment)
        }

    }

    private fun setLinearGauge(linearGauge: LinearGauge, days: Days): LinearGauge {
        linearGauge.data(SingleValueDataSet(arrayOf(days.currentBMI)))

        linearGauge.layout(Layout.HORIZONTAL)
        val scaleBarColorScale = OrdinalColor.instantiate()
        scaleBarColorScale.ranges(
            arrayOf(
                "{ from: 0, to: 18, color: ['red 1'] }",
                "{ from: 18, to: 25, color: ['green 1'] }",
                "{ from: 25, to: 30, color: ['yellow 1'] }",
                "{ from: 30, to: 40, color: ['orange 1'] }",
                "{ from: 40, to: 100, color: ['red 1'] }"
            )
        )

        linearGauge.scaleBar(0)
            .width("10%")
            .colorScale(scaleBarColorScale)

        linearGauge.marker(0)
            .type(MarkerType.TRIANGLE_DOWN)
            .color("red")
            .offset("-3.5%")
            .zIndex(10)
            .labels(days.currentBMI.toString())

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