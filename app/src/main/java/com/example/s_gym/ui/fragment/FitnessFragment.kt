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
import com.example.s_gym.database.entity.FitnessAdvance
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
    private val args by navArgs<FitnessFragmentArgs>()
    private lateinit var callback: OnBackPressedCallback
    private lateinit var fitness: FitnessAdvance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFitnessBinding.inflate(layoutInflater)
        fitness = arguments?.getParcelable(ARG_FITNESS) ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var positions = 0
        var restDialog = RestDialog()
        var fitnessAdvance = args.argsFitnessAdvanced?.exercisesList
        var fitnessBasic = args.argsFitnessDay?.exercise

        passDataToView(positions)

        if (fitnessBasic != null) {
            binding.btnNext.setOnClickListener {
                if (positions >= fitnessBasic.size - 1) {
                    positions = 0
                    findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
                } else {
                    restDialog.setCompleted(
                        positions + 1,
                        fitnessBasic.size
                    )
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
                if (positions >= fitnessBasic.size - 1) {
                    positions = 0
                    findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
                } else {
                    restDialog.setCompleted(
                        positions + 1,
                        fitnessBasic.size
                    )
                    positions++
                    restDialog.show(parentFragmentManager, "RestFragment")
                    passDataToView(positions)
                }
            }

            callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Chuyển hướng sang Fragment C khi nhấn nút Back button
                    var pauseExerciseDialog = PauseExerciseDialog()
                    pauseExerciseDialog.setExerciseImg(fitnessBasic[positions].urlVideoGuide)
                    pauseExerciseDialog.setExerciseName(fitnessBasic[positions].name)
                    pauseExerciseDialog.show(parentFragmentManager, "PauseExerciseDialog")
                }
            }
        }

        else if (fitnessAdvance != null) {
            binding.btnNext.setOnClickListener {
                if (positions >= fitnessAdvance.size - 1) {
                    positions = 0
                    findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
                } else {
                    restDialog.setCompleted(
                        positions + 1,
                        fitnessAdvance.size
                    )
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
                if (positions >= fitnessAdvance.size - 1) {
                    positions = 0
                    findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
                } else {
                    restDialog.setCompleted(
                        positions + 1,
                        fitnessAdvance.size
                    )
                    positions++
                    restDialog.show(parentFragmentManager, "RestFragment")
                    passDataToView(positions)
                }
            }

            callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Chuyển hướng sang Fragment C khi nhấn nút Back button
                    var pauseExerciseDialog = PauseExerciseDialog()
                    pauseExerciseDialog.setExerciseImg(fitnessAdvance[positions].urlVideoGuide)
                    pauseExerciseDialog.setExerciseName(fitnessAdvance[positions].name)
                    pauseExerciseDialog.show(parentFragmentManager, "PauseExerciseDialog")
                }
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
        if(args.argsFitnessAdvanced != null) {
            val exerciseList = args.argsFitnessAdvanced!!.exercisesList[positions]
            Glide.with(context).load(exerciseList.urlVideoGuide).into(binding.imgAnimationExercise)
            binding.txtExerName.text = exerciseList.name
            binding.txtAmount.text = "x${exerciseList.animationMount}"
            requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
        }

        else if (args.argsFitnessDay != null) {
            val exerciseList = args.argsFitnessDay!!.exercise[positions]
            Glide.with(context).load(exerciseList.urlVideoGuide).into(binding.imgAnimationExercise)
            binding.txtExerName.text = exerciseList.name
            binding.txtAmount.text = "x${exerciseList.animationMount}"
            requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
        }
    }

    companion object {
        private const val ARG_FITNESS = "fitness"

        fun newInstance(fitness: FitnessAdvance): FitnessFragment {
            val args = Bundle()
            args.putParcelable(ARG_FITNESS, fitness)
            val fragment = FitnessFragment()
            fragment.arguments = args
            return fragment
        }
    }
}