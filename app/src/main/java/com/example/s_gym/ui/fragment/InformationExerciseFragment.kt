package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentInformationExerciseBinding

/**
 * A simple [Fragment] subclass.
 * Use the [InformationExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformationExerciseFragment : Fragment() {
    private lateinit var binding: FragmentInformationExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
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
     binding.txtIconClose.setOnClickListener {
         var intent = Intent(requireActivity(), AddFitnessFragment::class.java)
         startActivity(intent)
     }
    }
}