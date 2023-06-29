package com.example.s_gym.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.MainActivity
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.ui.adapter.BasicPlanAdapter
import com.example.s_gym.databinding.FragmentBasicPlanBinding
import com.example.s_gym.ui.viewmodel.BasicPlanViewModel
import kotlinx.coroutines.launch


class BasicPlanFragment : Fragment() {
    private lateinit var binding: FragmentBasicPlanBinding
    private lateinit var basicPlanAdapter: BasicPlanAdapter
    private lateinit var viewModelFactory: BasicPlanViewModel.BasicPlanViewModelFactory
    private lateinit var viewModel: BasicPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory =
            BasicPlanViewModel.BasicPlanViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[BasicPlanViewModel::class.java]
    }

    interface onBasicPlanItemClickListener {
        fun onBasicPlanItemClick(fitnessBasic: FitnessBasic)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasicPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        for(day in fitnessDays) {
//            viewModel.copyFitnessDayToBasic(day)
//        }

        binding.progressBarPlan.max = viewModel.getNumberOfDaysInCurrentMonth()

        var listBasicFitness = emptyList<FitnessBasic>()
        basicPlanAdapter = BasicPlanAdapter(emptyList())
        binding.rvBasicPlan.layoutManager = LinearLayoutManager(context)
        binding.rvBasicPlan.adapter = basicPlanAdapter
        lifecycleScope.launch{
            val user = MainActivity.currentFirebaseUser
                viewModel.allBasic(user!!.uid).observe(viewLifecycleOwner) {
                listBasicFitness = viewModel.getFitnessBasicFlowMonth(it)
                basicPlanAdapter.setFitnessBasicList(listBasicFitness)
                basicPlanAdapter.notifyDataSetChanged()

                binding.txtLeftDate.text = "${viewModel.getLeftDay(it)} ngày còn lại"

                binding.txtCompletedLevel.text = "${String.format("%.2f", viewModel.getCompletedLevel(it)*100)}%"
                binding.progressBarPlan.progress = viewModel.getCompletedFitness(it)
            }
        }
        binding.cvBackdrop.setOnClickListener {
            viewModel.deleteAll()
        }

        basicPlanAdapter.setItemClickListener(object : onBasicPlanItemClickListener {
            override fun onBasicPlanItemClick(fitnessBasic: FitnessBasic) {
                val action =
                    BasicPlanFragmentDirections.actionBasicPlanFragmentToBasicFitnessFragment(
                        fitnessBasic
                    )
                findNavController().navigate(action)
            }
        })
    }
}



