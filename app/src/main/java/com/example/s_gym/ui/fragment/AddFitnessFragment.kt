package com.example.s_gym.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.ui.adapter.AddFitnessAdapter
import com.example.s_gym.database.entity.Exercise
import com.example.s_gym.databinding.FragmentAddFitnessBinding


class AddFitnessFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var exerciseList: List<Exercise>

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var addFitnessAdapter: AddFitnessAdapter
    private lateinit var informationExerciseFragment: InformationExerciseFragment

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exerciseList = getListExercise()
        addFitnessAdapter = AddFitnessAdapter(exerciseList)

        binding.rvAddFitness.adapter = addFitnessAdapter

        //Xử lý khi click vào item
        addFitnessAdapter.setItemClickListener(object : onItemClickListener {

            override fun onItemClick(position: Int) {
                informationExerciseFragment = InformationExerciseFragment()
                fragmentTransaction = supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.ac_add_fitness, informationExerciseFragment)
                    .commit()
            }
        })
        binding.rvAddFitness.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.btnBack.setOnClickListener {
            finish()
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