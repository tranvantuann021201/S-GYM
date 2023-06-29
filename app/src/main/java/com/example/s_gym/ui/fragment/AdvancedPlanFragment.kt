package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.MainActivity
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentAdvancedPlanBinding
import com.example.s_gym.ui.adapter.AdvancePlanAdapter
import com.example.s_gym.ui.dialog.NameMyExerciseDialogFragment
import com.example.s_gym.ui.viewmodel.AdvancedPlanViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AdvancedPlanFragment : Fragment() {
    private  lateinit var binding : FragmentAdvancedPlanBinding
    private lateinit var viewModelFactory: AdvancedPlanViewModel.AdvancedPlanViewModelFactory
    private lateinit var viewModel: AdvancedPlanViewModel
    private lateinit var advancePlanAdapter: AdvancePlanAdapter

    interface onMoreIconClickListener {
        fun onMoreIconClick(position: Int, view: View)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = AdvancedPlanViewModel.AdvancedPlanViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[AdvancedPlanViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAdvancedPlanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deleteEmptyFitnessAdvance()

        binding.cvBackdrop.setOnClickListener {
            viewModel.deleteAllFromFitnessAdvance()
            Toast.makeText(context, "FitnessAdvance list deleted all ((;", Toast.LENGTH_SHORT).show()
        }

        binding.addNewFitness.setOnClickListener {
            val fitnessAdvance = FitnessAdvance(0, "name", listOf(), MainActivity.currentFirebaseUser!!.uid)
            lifecycleScope.launch {
                val newId = viewModel.addFitnessAdvance(fitnessAdvance)
                viewModel.addFitnessAdvanceToCloud(newId)
                Log.e("=================FitnessAdvanceID", newId.toString())
                val action = AdvancedPlanFragmentDirections.actionAdvancedPlanFragmentToAddFitnessFragment(fitnessAdvance.copy(id = newId.toInt()), "fromAdvancePlan")
                findNavController().navigate(action)
            }
        }
        var fitnessAdvanceList = emptyList<FitnessAdvance>()
        advancePlanAdapter = AdvancePlanAdapter(fitnessAdvanceList)
        binding.rvAdvancedPlan.adapter = advancePlanAdapter
        binding.rvAdvancedPlan.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val fitnessList = viewModel.readAllData(viewModel.currentUser!!.uid)
            withContext(Dispatchers.Main) {
                fitnessList.observe(viewLifecycleOwner) { fitnessList ->
                    fitnessAdvanceList = fitnessList
                    advancePlanAdapter.setFitnessAdvanceList(fitnessAdvanceList)
                    advancePlanAdapter.notifyDataSetChanged()
                }
            }
        }

        advancePlanAdapter.setItemClickListener(object : onMoreIconClickListener {
            override fun onMoreIconClick(position: Int, view: View) {
                val fitnessAdvance = fitnessAdvanceList[position]
                val popupMenu = PopupMenu(view.context, view)
                popupMenu.menuInflater.inflate(R.menu.more_in_item_advance_plan, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.rename -> {
                            val dialogFragment = NameMyExerciseDialogFragment.newInstance(fitnessAdvance)
                            dialogFragment.show(parentFragmentManager, "NameMyExerciseDialogFragment")
                            true
                        }
                        R.id.delete -> {
                            viewModel.deleteFitnessAdvance(fitnessAdvance)

                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        })
    }
}