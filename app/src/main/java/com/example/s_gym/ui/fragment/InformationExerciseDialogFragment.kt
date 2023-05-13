package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.s_gym.databinding.FragmentInformationExerciseBinding

/**
 * A simple [Fragment] subclass.
 * Use the [InformationExerciseDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformationExerciseDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentInformationExerciseBinding
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
     }
    }
}