package com.example.s_gym.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.s_gym.R
import com.example.s_gym.databinding.FragmentRestBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestFragment : DialogFragment() {
    private lateinit var binding: FragmentRestBinding
    private lateinit var timer: CountDownTimer
    var timeLeft = 6000L // Thời gian ban đầu của bộ đếm ngược
    private var position: Int = 0
    private var size: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = createCountDownTimer(timeLeft)

        binding.btn20sPlus.setOnClickListener {
            timer.cancel()
            timeLeft += 20000L
            timer = createCountDownTimer(timeLeft)
            timer.start()
        }

        binding.btnRestSkip.setOnClickListener {
            timer.cancel()
            dialog?.cancel()
        }

        binding.txtCompleted.text = "Đã hoàn thành ${getComplete()}"
    }

    override fun onResume() {
        super.onResume()
        timer.cancel()
        Log.e("state: ", "onResume <================================")
        timeLeft = 6000L
        timer = createCountDownTimer(timeLeft)
        timer.start()
    }

    fun createCountDownTimer(timeLeft: Long): CountDownTimer {
        return object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(p0: Long) {
                this@RestFragment.timeLeft = p0
                val minutes = (p0 / 1000) / 60
                val seconds = (p0 / 1000) % 60
                binding.txtRestTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                dialog?.cancel()
            }
        }
    }
    fun setCompleted(position: Int, size: Int) {
        this.position = position
        this.size = size
    }

    fun getComplete(): String {
        return "$position/$size"
    }
}