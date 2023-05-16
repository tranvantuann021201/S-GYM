package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.ui.adapter.AddFitnessAdapter
import com.example.s_gym.databinding.FragmentAddFitnessBinding
import com.example.s_gym.ui.viewmodel.AddFitnessViewModel
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel


class AddFitnessFragment : Fragment() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var addFitnessAdapter: AddFitnessAdapter
    private val args by navArgs<AddFitnessFragmentArgs>()

    private lateinit var viewModelFactory: AddFitnessViewModel.AddFitnessViewModelFactory
    private lateinit var viewModel: AddFitnessViewModel


    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = AddFitnessViewModel.AddFitnessViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddFitnessViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddFitnessBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadExercises(requireContext())
        addFitnessAdapter = AddFitnessAdapter(viewModel.exerciseList)
        binding.rvAddFitness.adapter = addFitnessAdapter

        //Xử lý khi click vào item
        addFitnessAdapter.setItemClickListener(object : onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = AddFitnessFragmentDirections.actionAddFitnessFragmentToInformationExerciseDialogFragment(viewModel.exerciseList[position], args.argsFitnessAdvance)
                findNavController().navigate(action)
            }
        })
        binding.rvAddFitness.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

