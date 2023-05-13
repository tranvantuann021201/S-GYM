package com.example.s_gym.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.ui.adapter.BasicPlanAdapter
import com.example.s_gym.database.entity.FitnessDay
import com.example.s_gym.databinding.FragmentBasicPlanBinding
import com.example.s_gym.ui.viewmodel.BasicPlanViewModel


class BasicPlanFragment : Fragment() {
    private lateinit var binding: FragmentBasicPlanBinding
    private lateinit var basicPlanAdapter: BasicPlanAdapter
    private val viewModel: BasicPlanViewModel by viewModels()

    interface onBasicPlanItemClickListener {
        fun onBasicPlanItemClick(fitnessDay: FitnessDay)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasicPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fitnessDays = viewModel.getFitnessDays(requireContext())
        basicPlanAdapter = BasicPlanAdapter(fitnessDays)
        binding.rvBasicPlan.layoutManager = LinearLayoutManager(context)
        binding.rvBasicPlan.adapter = basicPlanAdapter

        basicPlanAdapter.setItemClickListener(object : onBasicPlanItemClickListener {
            override fun onBasicPlanItemClick(fitnessDay: FitnessDay) {
                val action =
                    BasicPlanFragmentDirections.actionBasicPlanFragmentToBasicFitnessFragment(fitnessDay)
                findNavController().navigate(action)
            }
        })
    }
}



