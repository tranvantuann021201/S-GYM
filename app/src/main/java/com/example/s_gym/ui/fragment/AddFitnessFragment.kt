package com.example.s_gym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.api.Exercise
import com.example.s_gym.ui.adapter.AddFitnessAdapter
import com.example.s_gym.database.entity.FitnessPlan
import com.example.s_gym.databinding.FragmentAddFitnessBinding
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset


class AddFitnessFragment : Fragment() {
    private lateinit var binding: FragmentAddFitnessBinding
    private lateinit var exerciseList: List<Exercise>
    private lateinit var addFitnessAdapter: AddFitnessAdapter

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

        try {
            val jsonString = getJSONFromAssets()!!
            val fitnessPlan = Gson().fromJson(jsonString, FitnessPlan::class.java)

            val exerciseSet = mutableSetOf<Exercise>()
            for (day in fitnessPlan.fitnessPlan) {
                for (exercise in day.exercise) {
                    if(day.id != exercise.id) {
                        exerciseSet.add(exercise)
                    }
                }
            }
            exerciseList = exerciseSet.toList().sortedBy { it.id }
            addFitnessAdapter = AddFitnessAdapter(exerciseList)
            binding.rvAddFitness.adapter = addFitnessAdapter
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }

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
    private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = requireContext().assets.open("fitness.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}