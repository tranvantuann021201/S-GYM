package com.example.s_gym.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentUpdateBMIDialogBinding
import com.example.s_gym.ui.viewmodel.ReportViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateBMIDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateBMIDialog : DialogFragment() {
    private lateinit var binding: FragmentUpdateBMIDialogBinding
    private lateinit var viewModelFactory: UpdateBMIViewModel.UpdateBMIViewModelFactory
    private lateinit var viewModel: UpdateBMIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = UpdateBMIViewModel.UpdateBMIViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[UpdateBMIViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBMIDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancelBmiDialog.setOnClickListener {
            dialog?.cancel()
        }

        viewModel.latestDay.observe(viewLifecycleOwner){day ->
            binding.edtWeight.hint = day.weight.toString()
            binding.edtHeight.hint = day.height.toString()
        }

        binding.btnSaveBmiDialog.setOnClickListener {
            val weight = binding.edtWeight.text.toString().toDoubleOrNull()
            val height = binding.edtHeight.text.toString().toDoubleOrNull()
            if (weight != null || height != null) {
                viewModel.updateLatestDay(weight, height)
                dismiss()
            }
            else {
                Toast.makeText(context, "Ohh!! Hình như bạn quên gì đó.", Toast.LENGTH_SHORT).show()
            }
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
}