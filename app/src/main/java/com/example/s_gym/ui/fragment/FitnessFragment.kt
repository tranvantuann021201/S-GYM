package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        passDataToView(positions)

        binding.btnNext.setOnClickListener {
            positions++
            if (positions >= args.argsFitnessDay.exercise.size) {
                positions = 0
                Toast.makeText(context, "Quay lại bài tập đầu tiên", Toast.LENGTH_SHORT).show()
            }
            passDataToView(positions)

        }
        binding.btnPrevious.setOnClickListener {
            positions--
            if (positions == 0) {
                Toast.makeText(context, "Đây là bài tập đầu tiên", Toast.LENGTH_SHORT).show()
            }
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