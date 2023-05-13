package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s_gym.ui.adapter.EditBasicFitnessAdapter
import com.example.s_gym.databinding.FragmentEditBasicFitnessBinding
import com.example.s_gym.ui.adapter.OnStartDragListener
import com.example.s_gym.ui.touch.ItemTouchHelperCallback
import com.example.s_gym.ui.viewmodel.EditBasicFitnessViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditBasicFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditBasicFitnessFragment : Fragment(), OnStartDragListener {
    private lateinit var binding: FragmentEditBasicFitnessBinding
    private lateinit var editBasicFitnessAdapter: EditBasicFitnessAdapter
    private val args by navArgs<BasicFitnessFragmentArgs>()
    private lateinit var touchHelper: ItemTouchHelper
    private val viewModel = EditBasicFitnessViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBasicFitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exerciseList = args.argsFitnessDay.exercise as MutableList
        editBasicFitnessAdapter = EditBasicFitnessAdapter(viewModel.exerciseList, this)
        binding.rvEditBasicFitness.adapter = editBasicFitnessAdapter

        val callback = ItemTouchHelperCallback(editBasicFitnessAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvEditBasicFitness)

        // Set click listener for btnSave
        binding.btnSave.setOnClickListener {
            viewModel.sortedExercises = editBasicFitnessAdapter.getSortedExercises()
            // Save the sorted list of exercises
            editBasicFitnessAdapter = EditBasicFitnessAdapter(viewModel.sortedExercises, this)
            findNavController().popBackStack()
            Toast.makeText(context, "Chỉnh sửa đã được lưu", Toast.LENGTH_SHORT).show()
        }
        binding.rvEditBasicFitness.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }
}


