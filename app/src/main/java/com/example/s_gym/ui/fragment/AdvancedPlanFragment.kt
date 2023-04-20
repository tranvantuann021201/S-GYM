package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentAdvancedPlanBinding


class AdvancedPlanFragment : Fragment() {
    private  lateinit var addvancebin : FragmentAdvancedPlanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addvancebin =  FragmentAdvancedPlanBinding.inflate(layoutInflater)
        return addvancebin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addExerciseFitness = addvancebin.addExerciesfiness;

        addExerciseFitness.setOnClickListener {
            val intent  = Intent(requireActivity(),AddFitnessFragment::class.java)
            startActivity(intent)
        }
    }


}