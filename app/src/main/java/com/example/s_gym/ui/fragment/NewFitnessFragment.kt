package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentNewFitnessBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NewFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFitnessFragment : Fragment() {
    private lateinit var binding: FragmentNewFitnessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewFitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvBtnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_newFitnessFragment_to_addFitnessFragment)
        }
    }
}