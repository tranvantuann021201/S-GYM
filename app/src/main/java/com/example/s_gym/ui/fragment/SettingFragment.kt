package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentSettingBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtMyProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_myProfileFragment)
        }

        binding.txtExerSetting.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_fitnessSettingFragment)
        }

        binding.txtRemind.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_remindFragment)
        }
    }
}