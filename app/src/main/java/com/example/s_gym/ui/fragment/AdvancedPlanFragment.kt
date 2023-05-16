package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentAdvancedPlanBinding
import com.example.s_gym.ui.viewmodel.AdvancedPlanViewModel
import kotlinx.coroutines.launch


class AdvancedPlanFragment : Fragment() {
    private  lateinit var binding : FragmentAdvancedPlanBinding
    private lateinit var viewModelFactory: AdvancedPlanViewModel.AdvancedPlanViewModelFactory
    private lateinit var viewModel: AdvancedPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = AdvancedPlanViewModel.AdvancedPlanViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AdvancedPlanViewModel::class.java]
    }

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
        viewModel.deleteEmptyFitnessAdvance()
        val addExerciseFitness = binding.addNewFitness

        addExerciseFitness.setOnClickListener {
            val fitnessAdvance = FitnessAdvance(0, "name", false, 0, listOf())
            lifecycleScope.launch {
                val newId = viewModel.addFitnessAdvance(fitnessAdvance)
                Log.e("=================FitnessAdvanceID", newId.toString())
                val action = AdvancedPlanFragmentDirections.actionAdvancedPlanFragmentToAddFitnessFragment(fitnessAdvance.copy(id = newId.toInt()))
                findNavController().navigate(action)
            }
        }

    }
}