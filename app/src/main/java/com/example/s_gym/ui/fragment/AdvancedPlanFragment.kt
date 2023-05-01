package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s_gym.databinding.FragmentAdvancedPlanBinding


class AdvancedPlanFragment : Fragment() {
    private  lateinit var binding : FragmentAdvancedPlanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAdvancedPlanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addExerciseFitness = binding.addExerciesfiness;

        addExerciseFitness.setOnClickListener {
            val intent  = Intent(requireActivity(),AddFitnessFragment::class.java)
            startActivity(intent)
        }
    }


}