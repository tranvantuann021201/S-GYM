package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentInformationExerciseBinding
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [InformationExerciseDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class InformationExerciseDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentInformationExerciseBinding
    private val args by navArgs<InformationExerciseDialogFragmentArgs>()
    private lateinit var viewModelFactory: InformationExerciseViewModel.InformationExerciseViewModelFactory
    private lateinit var viewModel: InformationExerciseViewModel
    private lateinit var exercisesList: MutableList<Exercises>
    private lateinit var exercises: Exercises

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        viewModelFactory = InformationExerciseViewModel.InformationExerciseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InformationExerciseViewModel::class.java]

//        exercises = viewModel.convertExerciseToExercises(args.argsExercise)
//        exercisesList = mutableListOf(exercises)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInformationExerciseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passDataToView()
        binding.txtIconClose.setOnClickListener {
            dialog?.cancel()
        }

        viewModel.exerciseAmount.observe(viewLifecycleOwner) { amount ->
            binding.txtExerAmount.text = "x$amount"
        }

        binding.btnPlus.setOnClickListener {
            viewModel.increaseAmount()
        }

        binding.btnMinus.setOnClickListener {
            viewModel.decreaseAmount()
        }

        exercisesList = args.argsFitnessAdvance.exercisesList.toMutableList()

        binding.btnAddAnimation.setOnClickListener {

            exercises = viewModel.convertExerciseToExercises(args.argsExercise)
            val animationMount = viewModel.exerciseAmount.value ?: 10
            exercises = viewModel.convertExerciseToExercises(args.argsExercise)
            exercisesList.add(exercises.copy(animationMount = animationMount))
            updateExerciseList()

            viewModel.addExerciseToFitnessAdvance(
                args.argsFitnessAdvance.id,
                exercises.copy(animationMount = animationMount)
            )

            val newFitnessAdvance = args.argsFitnessAdvance.copy(exercisesList = exercisesList)

            if (args.source == "fromAdvanceFitnessFragment") {
                val action =
                    InformationExerciseDialogFragmentDirections.actionInformationExerciseDialogFragmentToNewFitnessFragment(
                        newFitnessAdvance,
                        "fromAdvanceFitnessFragment"
                    )
                findNavController().navigate(action)
            } else {
                val action =
                    InformationExerciseDialogFragmentDirections.actionInformationExerciseDialogFragmentToNewFitnessFragment(
                        newFitnessAdvance,
                        "fromAdvancePlan"
                    )
                findNavController().navigate(action)
            }
        }

    }

    private fun passDataToView() {
        val argsExercise = args.argsExercise
        Glide.with(context).load(argsExercise.urlVideoGuide).into(binding.imgAnimationExercise)
        binding.txtAnimationName.text = argsExercise.name
        binding.txtExerAmount.text = "x${argsExercise.animationMount}"
        requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
    }

    private fun updateExerciseList() {
        args.argsFitnessAdvance.exercisesList = exercisesList

        // Thông báo cho ViewModel rằng một đối tượng FitnessAdvance đã được cập nhật
        viewModel.updateFitnessAdvance(args.argsFitnessAdvance)
    }
}