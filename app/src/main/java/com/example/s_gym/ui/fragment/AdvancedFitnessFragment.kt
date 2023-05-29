package com.example.s_gym.ui.fragment

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.MainActivity
import com.example.s_gym.R
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentAdvancedFitnessBinding
import com.example.s_gym.ui.adapter.AdvancedFitnessAdapter
import com.example.s_gym.ui.viewmodel.AdvancedFitnessViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AdvancedFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdvancedFitnessFragment : Fragment() {
    private lateinit var binding: FragmentAdvancedFitnessBinding
    private val args by navArgs<AdvancedFitnessFragmentArgs>()
    private lateinit var advancedFitnessAdapter: AdvancedFitnessAdapter
    private lateinit var viewModel: AdvancedFitnessViewModel
    private lateinit var fitnessAdvance: FitnessAdvance
    private lateinit var exercisesList: List<Exercises>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AdvancedFitnessViewModel::class.java]
        fitnessAdvance = args.argsFitnessAdvance
        exercisesList = fitnessAdvance.exercisesList

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvancedFitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitleActionBar.text = fitnessAdvance.name

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnStart.setOnClickListener{
            val action = AdvancedFitnessFragmentDirections.actionAdvancedFitnessFragmentToFitnessFragment(null, fitnessAdvance)
            findNavController().navigate(action)
        }

        advancedFitnessAdapter = AdvancedFitnessAdapter(exercisesList)
        binding.rvAdvancedFiness.adapter = advancedFitnessAdapter
        binding.rvAdvancedFiness.layoutManager = LinearLayoutManager(activity)

        viewModel.fitnessAdvanceLiveData.observe(viewLifecycleOwner) { updatedFitnessAdvance ->
            advancedFitnessAdapter.updateData(updatedFitnessAdvance.exercisesList)
            advancedFitnessAdapter.notifyDataSetChanged()
        }

        binding.btnModifyFitness.setOnClickListener {
            val action = AdvancedFitnessFragmentDirections.actionAdvancedFitnessFragmentToNewFitnessFragment(fitnessAdvance, "fromAdvanceFitnessFragment")
            findNavController().navigate(action)
        }
    }
}
