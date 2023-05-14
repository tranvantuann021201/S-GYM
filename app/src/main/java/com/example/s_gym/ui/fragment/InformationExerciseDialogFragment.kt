package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.database.repository.FitnessRepository
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
    private val viewModel: InformationExerciseViewModel by viewModels ()
//        InformationExerciseViewModelFactory(repository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
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

        binding.btnAddAnimation.setOnClickListener {
//            val exercises = viewModel.convertExerciseToExercises(args.argsExercise)
//            viewModel.addExercises(exercises)
//            findNavController().navigate(R.id.action_informationExerciseDialogFragment_to_newFitnessFragment)
        }

    }

    private fun passDataToView() {
        val exercise = args.argsExercise
        Glide.with(context).load(exercise.urlVideoGuide).into(binding.imgAnimationExercise)
        binding.txtAnimationName.text = exercise.name
        binding.txtExerAmount.text = "x${exercise.animationMount}"
        requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
    }
}