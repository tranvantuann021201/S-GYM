package com.example.s_gym.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentFollowDrinkWaterBinding

class FollowDrinkWaterFragment : Fragment() {
    private lateinit var binding: FragmentFollowDrinkWaterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowDrinkWaterBinding.inflate(layoutInflater)
        return binding.root
    }
}