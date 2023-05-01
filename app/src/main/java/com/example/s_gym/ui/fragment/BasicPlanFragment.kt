package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.s_gym.R
import com.example.s_gym.adapter.BasicPlanAdapter
import com.example.s_gym.adapter.FragmentPlanPagerAdapter
import com.example.s_gym.database.FitnessDay
import com.example.s_gym.database.FitnessPlan
import com.example.s_gym.databinding.FragmentBasicPlanBinding
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.util.*


class BasicPlanFragment : Fragment() {

    private lateinit var binding: FragmentBasicPlanBinding
    private lateinit var basicPlanAdapter: BasicPlanAdapter
    val calendar = Calendar.getInstance()
    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    @RequiresApi(Build.VERSION_CODES.O)
    val daysInRange = (1..daysInMonth).map { day ->
        LocalDateTime.of(year, month + 1, day, 0, 0, 0)
    }
    interface onBasicPlanItemClickListener {
        fun onBasicPlanItemClick(fitnessDay: FitnessDay)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val jsonString = getJSONFromAssets()!!
            val fitnessPlan = Gson().fromJson(jsonString, FitnessPlan::class.java)

            val fitnessDays = fitnessPlan.fitnessPlan
            val fitnessDayList = fitnessDays.filter { fitnessDay ->
                daysInRange.any { day ->
                    fitnessDay.id == day.dayOfMonth
                }
            }
            basicPlanAdapter =  BasicPlanAdapter(fitnessDayList)
            binding.rvBasicPlan.layoutManager = LinearLayoutManager(context)
            binding.rvBasicPlan.adapter = basicPlanAdapter
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }

        basicPlanAdapter.setItemClickListener(object : onBasicPlanItemClickListener{
            override fun onBasicPlanItemClick(fitnessDay: FitnessDay) {
                val action = BasicPlanFragmentDirections.actionBasicPlanFragmentToBasicFitnessFragment(fitnessDay)
                findNavController().navigate(action)
            }

        })
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
