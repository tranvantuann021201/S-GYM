package com.example.s_gym.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentNameMyExerciseDialogBinding
import com.example.s_gym.ui.fragment.NewFitnessFragmentArgs
import com.example.s_gym.ui.viewmodel.AddFitnessViewModel
import com.example.s_gym.ui.viewmodel.NameMyExercisesViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NameMyExerciseDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NameMyExerciseDialogFragment() : DialogFragment() {
    lateinit var binding: FragmentNameMyExerciseDialogBinding
    private lateinit var viewModelFactory: NameMyExercisesViewModel.NameMyExercisesViewModelFactory
    private lateinit var viewModel: NameMyExercisesViewModel
    private lateinit var fitnessAdvance: FitnessAdvance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fitnessAdvance = arguments?.getParcelable<FitnessAdvance>("fitnessAdvance")!!

        viewModelFactory =
            NameMyExercisesViewModel.NameMyExercisesViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[NameMyExercisesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNameMyExerciseDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSaveNameExercise.setOnClickListener {
            var fitnessAdvanceName = binding.edtNameMyExer.text.toString()
            if (fitnessAdvanceName == "") {
                fitnessAdvanceName = "Bài tập số ${fitnessAdvance.id}"
            }
            viewModel.updateFitnessAdvanceName(fitnessAdvance.id, fitnessAdvanceName)
            findNavController().navigate(R.id.action_nameMyExerciseDialog_to_planFragment2)
            Log.d("NameMyExerciseDialog", "Cancel button clicked")

        }

        binding.btnCancelDialog.setOnClickListener {
            Log.d("NameMyExerciseDialog", "Cancel button clicked")
            dialog?.cancel()
        }
    }

    override fun onStart() {
        super.onStart()
        val window = requireActivity().window

        window?.setGravity(Gravity.CENTER_HORIZONTAL)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(fitnessAdvance: FitnessAdvance) : NameMyExerciseDialogFragment {
            val f = NameMyExerciseDialogFragment()
            // Supply fitnessAdvance input as an argument.
            val args = Bundle()
            args.putParcelable("fitnessAdvance", fitnessAdvance)
            f.arguments = args
            return f
        }
    }

}
