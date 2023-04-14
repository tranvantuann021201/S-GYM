package com.example.s_gym.PlanFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentBasicPlanBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicPlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicPlanFragment : Fragment() {

    private lateinit var binding: FragmentBasicPlanBinding
    private var progr = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBasicPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProgressBar()

        binding.buttonIncr.setOnClickListener {
            if (progr >= 10) {
                progr -= 10
                updateProgressBar()
            }
        }

        binding.buttonDecr.setOnClickListener {
            if (progr <= 90) {
                progr += 10
                updateProgressBar()
            }
        }
    }

    private fun updateProgressBar() {
        binding.progressBarDay.progress = progr
        binding.textViewProgress.text = "$progr%"
    }

    companion object {


        fun newInstance(param1: String, param2: String) =
            BasicPlanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}