package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.adapter.AddFitnessAdapter
import com.example.s_gym.api.WorkoutApiService
import com.example.s_gym.database.Exercise
import com.example.s_gym.database.FitnessDay
import com.example.s_gym.databinding.FragmentAddFitnessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFitnessFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var exerciseList: List<Exercise>

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var addFitnessAdapter: AddFitnessAdapter
    private lateinit var  informationExerciseFragment: InformationExerciseFragment
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
        addFitnessAdapter.setItemClickListener(object : onItemClickListener{

            override fun onItemClick(position: Int) {
                informationExerciseFragment = InformationExerciseFragment()
                fragmentTransaction = supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.ac_add_fitness,informationExerciseFragment).commit()
            }
        })
        addFitnessAdapter.setData(exerciseList)
        binding.rvAddFitness.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }



    private fun getListExercise(): List<Exercise> {
        val list: ArrayList<Exercise> = ArrayList()
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        list.add(Exercise(1, "abc", "1", "abc", false, 12.1))
        return list
    }

    private fun callApi() {
        WorkoutApiService.service.getExercises().enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>?, response: Response<List<Exercise>>?) {
                val exercisesList = response?.body()
                //Xử lý danh sách bài tập ở đây
                if (exercisesList != null) {
                    addFitnessAdapter = AddFitnessAdapter(exerciseList)
                    addFitnessAdapter.setData(exercisesList)
                    binding.rvAddFitness.adapter = addFitnessAdapter
                }
                Toast.makeText(this@AddFitnessFragment, "Call success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Exercise>>?, t: Throwable?) {
                // Xử lý lỗi khi không kết nối được API
                Toast.makeText(this@AddFitnessFragment, "Call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}