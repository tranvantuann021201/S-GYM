package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.ui.adapter.BasicFitnessAdapter
import com.example.s_gym.databinding.FragmentBasicFitnessBinding

class BasicFitnessFragment : Fragment() {
    private lateinit var binding: FragmentBasicFitnessBinding
    private val args by navArgs<BasicFitnessFragmentArgs>()
    private lateinit var basicFitnessAdapter: BasicFitnessAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicFitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basicFitnessAdapter = BasicFitnessAdapter(args.argsFitnessDay.exercise)
        binding.rvBasicFitness.adapter = basicFitnessAdapter
        binding.rvBasicFitness.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.btnStartExer.setOnClickListener {
            val action = BasicFitnessFragmentDirections.actionBasicFitnessFragmentToFitnessFragment(args.argsFitnessDay, null)
            findNavController().navigate(action)
        }

        binding.btnModifyExer.setOnClickListener {
            val action = BasicFitnessFragmentDirections.actionBasicFitnessFragmentToEditBasicFitnessFragment2(args.argsFitnessDay)
            findNavController().navigate(action)
        }
    }
}