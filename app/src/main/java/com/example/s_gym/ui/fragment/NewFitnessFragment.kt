package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s_gym.R
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.FragmentNewFitnessBinding
import com.example.s_gym.ui.adapter.NewFitnessAdapter
import com.example.s_gym.ui.adapter.OnStartDragListener
import com.example.s_gym.ui.dialog.NameMyExerciseDialogFragment
import com.example.s_gym.ui.touch.ItemTouchHelperCallback
import com.example.s_gym.ui.viewmodel.InformationExerciseViewModel
import com.example.s_gym.ui.viewmodel.MyProfileViewModel
import com.example.s_gym.ui.viewmodel.NewFitnessViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [NewFitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFitnessFragment : Fragment(), OnStartDragListener {
    private lateinit var binding: FragmentNewFitnessBinding
    private val args by navArgs<NewFitnessFragmentArgs>()
    private lateinit var newFitnessAdapter: NewFitnessAdapter
    private lateinit var touchHelper: ItemTouchHelper
    private lateinit var viewModelFactory: NewFitnessViewModel.NewFitnessViewModelFactory
    private lateinit var viewModel: NewFitnessViewModel
    private lateinit var exercisesList: MutableList<Exercises>
    private lateinit var fitnessAdvance: FitnessAdvance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitnessAdvance = args.argsFitnessAdvance
        exercisesList = fitnessAdvance.exercisesList.toMutableList()

        viewModelFactory =
            NewFitnessViewModel.NewFitnessViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewFitnessViewModel::class.java]

        viewModel.exercisesListLiveData.observe(this) {
            // Cập nhật dữ liệu cho Adapter và cập nhật giao diện
            newFitnessAdapter.updateData(exercisesList)
            newFitnessAdapter.notifyDataSetChanged()
        }
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
        binding.btnAdd.setOnClickListener {
            val action = NewFitnessFragmentDirections.actionNewFitnessFragmentToAddFitnessFragment(
                fitnessAdvance
            )
            findNavController().navigate(action)
        }
        newFitnessAdapter = NewFitnessAdapter(exercisesList, this)
        binding.rvNewFitness.adapter = newFitnessAdapter

        viewModel.exercisesListLiveData.observe(viewLifecycleOwner) { exercisesList ->
            // Cập nhật dữ liệu cho Adapter
            newFitnessAdapter.updateData(exercisesList)
            // Cập nhật giao diện
            newFitnessAdapter.notifyDataSetChanged()
        }

        val callback = ItemTouchHelperCallback(newFitnessAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvNewFitness)

        binding.rvNewFitness.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.btnSave.setOnClickListener {
            val dialogFragment = NameMyExerciseDialogFragment.newInstance(fitnessAdvance)
            dialogFragment.show(parentFragmentManager, "NameMyExerciseDialogFragment")
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }
}