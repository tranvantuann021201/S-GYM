package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.databinding.FragmentRemindBinding
import com.example.s_gym.ui.viewmodel.RemindViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [RemindFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemindFragment : Fragment() {
    private lateinit var viewModelFactory: RemindViewModel.RemindViewModelFactory
    private lateinit var viewModel: RemindViewModel
    private lateinit var binding: FragmentRemindBinding
    var currentUser = MainActivity.currentFirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = RemindViewModel.RemindViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RemindViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRemindBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentUser != null) {
            var newSetting: Setting? = null
            viewModel.getSetting(currentUser!!.uid).observe(viewLifecycleOwner) {
                newSetting = it
                binding.swExerRemind.isChecked = it.fitnessMind
                binding.swDrinkWaterRemind.isChecked = it.drinkMind
                binding.txtCurrentTime.text = it.fitnessMindTime + " hàng ngày"
            }

            binding.btnEdit.setOnClickListener {
                newSetting?.let {
                    updateFitnessMindTime(openTimePicker(),it)
                }
            }

            binding.swExerRemind.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.bookRemindFitnessNotify(requireContext(), requireActivity())
                } else {
                    viewModel.cancelRemindFitnessNotify(requireContext(), requireActivity())
                }
                // Cập nhật dữ liệu trong bảng setting
                newSetting?.let {
                    it.fitnessMind = isChecked
                    viewModel.updateSetting(it)
                }
            }

            binding.swDrinkWaterRemind.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.bookRemindWaterNotify(requireContext(), requireActivity())
                } else {
                    viewModel.cancelRemindWaterNotify(requireContext(), requireActivity())
                }
                // Cập nhật dữ liệu trong bảng setting
                newSetting?.let {
                    it.drinkMind = isChecked
                    viewModel.updateSetting(it)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openTimePicker(): MaterialTimePicker {
        val isSystem24h = is24HourFormat(requireContext())
        val clockFormat = if(isSystem24h) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        return MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Đặt lịch")
            .setInputMode(INPUT_MODE_KEYBOARD)
            .build()
    }

    private fun updateFitnessMindTime(picker: MaterialTimePicker ,setting: Setting) {
        picker.show(childFragmentManager, "TAG")
        var formattedTime = "16:30"
        picker.addOnPositiveButtonClickListener {
            // Lấy giờ và phút mà người dùng đã chọn
            val hour = picker.hour
            val minute = picker.minute

            // Định dạng thời gian theo định dạng mong muốn
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            formattedTime = timeFormat.format(calendar.time)
            viewModel.updateSetting(setting.copy(fitnessMindTime = formattedTime))
            picker.dismiss()
        }
    }
}
