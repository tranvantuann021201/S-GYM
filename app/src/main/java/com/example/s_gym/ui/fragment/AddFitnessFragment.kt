package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.ui.adapter.AddFitnessAdapter
import com.example.s_gym.database.entity.Exercise
import com.example.s_gym.databinding.FragmentAddFitnessBinding


class AddFitnessFragment : Fragment() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var exerciseList: List<Exercise>

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var addFitnessAdapter: AddFitnessAdapter
    private lateinit var informationExerciseFragment: InformationExerciseFragment

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddFitnessBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseList = getListExercise()
        addFitnessAdapter = AddFitnessAdapter(exerciseList)

        binding.rvAddFitness.adapter = addFitnessAdapter

        //Xử lý khi click vào item
        addFitnessAdapter.setItemClickListener(object : onItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })
        binding.rvAddFitness.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getListExercise(): List<Exercise> {
        val list: ArrayList<Exercise> = ArrayList()
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1, 8))
        return list
    }
}