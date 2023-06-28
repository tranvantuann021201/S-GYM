package com.example.s_gym.ui.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.s_gym.until.NotifyRemindFitnessBR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
}
