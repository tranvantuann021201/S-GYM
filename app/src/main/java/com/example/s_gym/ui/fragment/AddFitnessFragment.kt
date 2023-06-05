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
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.ui.adapter.AddFitnessAdapter
import com.example.s_gym.databinding.FragmentAddFitnessBinding
import com.example.s_gym.ui.viewmodel.AddFitnessViewModel


class AddFitnessFragment : Fragment() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var addFitnessAdapter: AddFitnessAdapter
    private val args by navArgs<AddFitnessFragmentArgs>()

    private lateinit var viewModelFactory: AddFitnessViewModel.AddFitnessViewModelFactory
    private lateinit var viewModel: AddFitnessViewModel
    private lateinit var exercisesList: MutableList<Exercises>


    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesList = args.argsFitnessAdvance.exercisesList.toMutableList()

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

        var exercisesList = mutableListOf<Exercises>()
        viewModel.loadExercises(requireContext())
        addFitnessAdapter = AddFitnessAdapter(exercisesList)

        viewModel.allBasic.observe(viewLifecycleOwner) { allBasic ->
            exercisesList = viewModel.getAllExercise(allBasic)
            val remainingElements = exercisesList.filterNot { it in this.exercisesList }
            addFitnessAdapter = AddFitnessAdapter(remainingElements)
            binding.rvAddFitness.adapter = addFitnessAdapter
            binding.rvAddFitness.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addFitnessAdapter.notifyDataSetChanged()

            addFitnessAdapter.setItemClickListener(object : onItemClickListener {
                override fun onItemClick(position: Int) {
                    if(args.source == "fromAdvanceFitnessFragment") {
                        val action =
                            AddFitnessFragmentDirections.actionAddFitnessFragmentToInformationExerciseDialogFragment(
                                exercisesList[position],
                                args.argsFitnessAdvance, "fromAdvanceFitnessFragment"
                            )
                        findNavController().navigate(action)
                    }
                    else {
                        val action =
                            AddFitnessFragmentDirections.actionAddFitnessFragmentToInformationExerciseDialogFragment(
                                exercisesList[position],
                                args.argsFitnessAdvance, "fromAdvancePlan"
                            )
                        findNavController().navigate(action)
                    }
                }
            })
        }

        //Xử lý khi click vào item

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

