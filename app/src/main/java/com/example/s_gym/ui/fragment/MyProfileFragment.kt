package com.example.s_gym.ui.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentMyProfileBinding
import com.example.s_gym.ui.viewmodel.MyProfileViewModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [MyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyProfileFragment : Fragment() {
    private lateinit var binding: FragmentMyProfileBinding
    private val viewModel: MyProfileViewModel by viewModels()
    private val c: Calendar = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            // Cập nhật giao diện người dùng với ngày được chọn
            binding.txtBirthDay.text = "$date"
        }

        binding.btnEditBirthDay.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                R.style.MyDatePickerDialogTheme,
                { _, year, monthOfYear, dayOfMonth ->
                    // Xử lý khi người dùng chọn một ngày
                    val selectedDate = "$dayOfMonth Th${monthOfYear + 1} $year"
                    viewModel.updateSelectedDate(selectedDate)
                },
                year,
                month,
                day
            )
            dpd.show()
            dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0026AF"));
            dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0026AF"));
        }
    }
}