package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentAdvancedPlanBinding
import com.example.s_gym.ui.adapter.AdvancePlanAdapter
import com.example.s_gym.ui.viewmodel.AdvancedPlanViewModel
import kotlinx.coroutines.launch


class AdvancedPlanFragment : Fragment() {
    private  lateinit var binding : FragmentAdvancedPlanBinding
    private lateinit var viewModelFactory: AdvancedPlanViewModel.AdvancedPlanViewModelFactory
    private lateinit var viewModel: AdvancedPlanViewModel
    private lateinit var advancePlanAdapter: AdvancePlanAdapter

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

        binding.cvBackdrop.setOnClickListener {
            viewModel.deleteAllFromFitnessAdvance()
            Toast.makeText(context, "FitnessAdvance list deleted all ((;", Toast.LENGTH_SHORT).show()
        }

        binding.addNewFitness.setOnClickListener {
            val fitnessAdvance = FitnessAdvance(0, "name", false, 0, listOf())
            lifecycleScope.launch {
                val newId = viewModel.addFitnessAdvance(fitnessAdvance)
                Log.e("=================FitnessAdvanceID", newId.toString())
                val action = AdvancedPlanFragmentDirections.actionAdvancedPlanFragmentToAddFitnessFragment(fitnessAdvance.copy(id = newId.toInt()), "fromAdvancePlan")
                findNavController().navigate(action)
            }
        }

        advancePlanAdapter = AdvancePlanAdapter(emptyList())
        binding.rvAdvancedPlan.adapter = advancePlanAdapter
        binding.rvAdvancedPlan.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allFitness.observe(viewLifecycleOwner) { fitnessList ->
            advancePlanAdapter.setFitnessAdvanceList(fitnessList)
            advancePlanAdapter.notifyDataSetChanged()
        }
    }

}