package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentDoneFitnessBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DoneFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoneFitnessFragment : Fragment() {
    private lateinit var binding: FragmentDoneFitnessBinding

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
    }
}