package com.example.s_gym.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentUpdateBMIDialogBinding

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateBMIDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateBMIDialog : Fragment() {
    private lateinit var binding: FragmentUpdateBMIDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBMIDialogBinding.inflate(layoutInflater)
        return binding.root
    }
}