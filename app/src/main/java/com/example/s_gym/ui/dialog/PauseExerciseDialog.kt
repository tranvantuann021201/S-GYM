package com.example.s_gym.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentPauseExerciseDialogBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PauseExerciseDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class PauseExerciseDialog : DialogFragment(){
    private lateinit var binding: FragmentPauseExerciseDialogBinding
    private var exerciseName: String = ""
    private var exerciseImg: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        binding = FragmentPauseExerciseDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtPresentAnimationName.text = getExerciseName()
        Glide.with(context).load(getExerciseImg()).into(binding.imgPresentAnimation)

        binding.btnCancelDialog.setOnClickListener {
            dialog?.cancel()
        }
        binding.btnResetExercise.setOnClickListener {
            findNavController().navigate(R.id.action_pauseExerciseDialog_to_basicPlanFragment2)
            dialog?.cancel()
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun setExerciseName(exerciseName: String) {
        this.exerciseName = exerciseName
    }

    private fun getExerciseName(): String {
        return this.exerciseName
    }

    fun setExerciseImg(exerciseImg: String) {
        this.exerciseImg = exerciseImg
    }

    private fun getExerciseImg(): String {
        return this.exerciseImg
    }
}