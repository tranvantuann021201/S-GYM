package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s_gym.adapter.AddFitnessAdapter
import com.example.s_gym.api.WorkoutApiService
import com.example.s_gym.database.Exercise
import com.example.s_gym.databinding.FragmentAddFitnessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFitnessFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var exerciseList: List<Exercise>
    private lateinit var addFitnessAdapter: AddFitnessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        exerciseList = getListExercise()
        addFitnessAdapter = AddFitnessAdapter(exerciseList)
        binding.rvAddFitness.adapter = addFitnessAdapter
        binding.rvAddFitness.layoutManager =
            LinearLayout(, LinearLayout.VERTICAL, false)

    }

    private fun getListExercise(): List<Exercise> {
        val list: ArrayList<Exercise> = ArrayList()
        list.add(Exercise(1, "abc", 1, emptyList(), 0, "string", "uudi"))
        list.add(Exercise(2, "abc", 1, emptyList(), 0, "string", "uudi"))
        list.add(Exercise(3, "abc", 1, emptyList(), 0, "string", "uudi"))
        list.add(Exercise(4, "abc", 1, emptyList(), 0, "string", "uudi"))
        list.add(Exercise(5, "abc", 1, emptyList(), 0, "string", "uudi"))
        return list
    }

//    private fun init() {
//
//        WorkoutApiService.service.getExercises().enqueue(object : Callback<List<Exercise>> {
//            override fun onResponse(call: Call<List<Exercise>>?, response: Response<List<Exercise>>?) {
//                val exercisesList = response?.body()
//                //Xử lý danh sách bài tập ở đây
//                if (exercisesList != null) {
//                    addFitnessAdapter = AddFitnessAdapter(exerciseList)
//                    addFitnessAdapter.setData(this@AddFitnessFragment,exercisesList)
//                    binding.rvAddFitness.adapter = addFitnessAdapter
//                }
//                Toast.makeText(this@AddFitnessFragment, "Call success", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onFailure(call: Call<List<Exercise>>?, t: Throwable?) {
//                // Xử lý lỗi khi không kết nối được API
//                Toast.makeText(this@AddFitnessFragment, "Call failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}