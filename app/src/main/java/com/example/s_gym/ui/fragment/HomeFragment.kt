package com.example.s_gym.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private  lateinit var homebin:FragmentHomeBinding
    lateinit var bottomNav : BottomNavigationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homebin = FragmentHomeBinding.inflate(layoutInflater)

        return homebin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(PlanFragment())
        bottomNav = homebin.bottomNav
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(PlanFragment())
                    true
                }
                R.id.message -> {
                    loadFragment(ReportFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingFragment())
                    true
                }
                else -> {loadFragment(PlanFragment())
                    true}
            }
        }
    }
    private  fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}