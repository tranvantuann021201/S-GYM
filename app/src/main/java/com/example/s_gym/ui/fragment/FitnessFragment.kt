package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentFitnessBinding
import com.example.s_gym.ui.dialog.PauseExerciseDialog
import com.example.s_gym.ui.dialog.RestDialog

/**
 * A simple [Fragment] subclass.
 * Use the [FitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FitnessFragment : Fragment() {

    private lateinit var binding: FragmentFitnessBinding
    private val args by navArgs<BasicFitnessFragmentArgs>()
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFitnessBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var positions = 0
        var restDialog = RestDialog()

        passDataToView(positions)

        binding.btnNext.setOnClickListener {
            if (positions >= args.argsFitnessDay.exercise.size - 1) {
                positions = 0
                findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
            } else {
                restDialog.setCompleted(positions + 1, args.argsFitnessDay.exercise.size)
                positions++
                restDialog.show(parentFragmentManager, "RestFragment")
                passDataToView(positions)
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (positions == 0) {
                Toast.makeText(context, "Đây là bài tập đầu tiên", Toast.LENGTH_SHORT).show()
            } else
                positions--
            passDataToView(positions)
        }

        binding.btnDone.setOnClickListener {
            if (positions >= args.argsFitnessDay.exercise.size - 1) {
                positions = 0
                findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
            } else {
                restDialog.setCompleted(positions + 1, args.argsFitnessDay.exercise.size)
                positions++
                restDialog.show(parentFragmentManager, "RestFragment")
                passDataToView(positions)
            }

        }

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Chuyển hướng sang Fragment C khi nhấn nút Back button
                var pauseExerciseDialog = PauseExerciseDialog()
                pauseExerciseDialog.setExerciseImg(args.argsFitnessDay.exercise[positions].urlVideoGuide)
                pauseExerciseDialog.setExerciseName(args.argsFitnessDay.exercise[positions].name)
                pauseExerciseDialog.show(parentFragmentManager, "PauseExerciseDialog")
            }
        }
        // Đăng ký OnBackPressedCallback
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
    override fun onPause() {
        super.onPause()
        callback.remove()
    }

    private fun passDataToView(positions: Int) {
        val exerciseList = args.argsFitnessDay.exercise[positions]
        Glide.with(context).load(exerciseList.urlVideoGuide).into(binding.imgAnimationExercise)
        binding.txtExerName.text = exerciseList.name
        binding.txtAmount.text = "x${exerciseList.animationMount}"
        requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
    }
}