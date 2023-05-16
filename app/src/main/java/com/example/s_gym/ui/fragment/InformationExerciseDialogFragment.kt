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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        viewModelFactory = InformationExerciseViewModel.InformationExerciseViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[InformationExerciseViewModel::class.java]
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
            val exercises = viewModel.convertExerciseToExercises(args.argsExercise)
            val animationMount = viewModel.exerciseAmount.value ?: 10
            viewModel.addExerciseToFitnessAdvance(args.argsFitnessAdvance.id,exercises.copy(animationMount = animationMount))
            findNavController().navigate(R.id.action_informationExerciseDialogFragment_to_newFitnessFragment)
            Toast.makeText(requireContext(), exercises.id.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun passDataToView() {
        val argsExercise = args.argsExercise
        Glide.with(context).load(argsExercise.urlVideoGuide).into(binding.imgAnimationExercise)
        binding.txtAnimationName.text = argsExercise.name
        binding.txtExerAmount.text = "x${argsExercise.animationMount}"
        requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
    }
}