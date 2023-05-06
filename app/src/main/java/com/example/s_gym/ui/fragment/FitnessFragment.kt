package com.example.s_gym.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentFitnessBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FitnessFragment : Fragment() {

    private lateinit var binding: FragmentFitnessBinding
    private val args by navArgs<BasicFitnessFragmentArgs>()
    private lateinit var timer: CountDownTimer
    var timeLeft = 2000L // Thời gian ban đầu của bộ đếm ngược

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
        var dialog = RestFragment()

        passDataToView(positions)

        binding.btnNext.setOnClickListener {
            if (positions >= args.argsFitnessDay.exercise.size - 1) {
                positions = 0
                findNavController().navigate(R.id.action_fitnessFragment_to_doneFitnessFragment)
            } else {
                dialog.setCompleted(positions + 1, args.argsFitnessDay.exercise.size)
                positions++
                dialog.show(parentFragmentManager, "RestFragment")
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

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.btnDone.isEnabled = true
        }, 10000)

        binding.btnDone.setOnClickListener {
            positions++
            dialog.show(parentFragmentManager, "RestFragment")
            passDataToView(positions)
        }
    }

    private fun passDataToView(positions: Int) {
        val exerciseList = args.argsFitnessDay.exercise[positions]
        Glide.with(context).load(exerciseList.urlVideoGuide).into(binding.imgAnimationExercise)
        binding.txtExerName.text = exerciseList.name
        binding.txtAmount.text = "x${exerciseList.animationMount}"
        requireActivity().supportFragmentManager.findFragmentById(R.id.fitnessFragment)
    }
}