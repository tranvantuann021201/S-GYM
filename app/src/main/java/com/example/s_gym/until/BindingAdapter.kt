package com.example.s_gym.until

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.s_gym.R

object BindingAdapters {
    @SuppressLint("ResourceType")
    @BindingAdapter("drunk")
    fun setDrunk(textView: TextView, drunk: Int) {
        if (drunk == 8) {
            // Thay đổi hiển thị của TextView khi drunk bằng 8
            textView.text = textView.context.getString(R.drawable.baseline_check_circle_24)
        } else {
            // Thay đổi hiển thị của TextView khi drunk khác 8
            textView.text = drunk.toString()
        }
    }
}
