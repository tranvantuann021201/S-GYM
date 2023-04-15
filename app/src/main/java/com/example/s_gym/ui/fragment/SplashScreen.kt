package com.example.s_gym.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.s_gym.MainActivity
import com.example.s_gym.R

class SplashScreen : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed({
            // Chuyển đến Activity chính của ứng dụng sau khi splash screen kết thúc.
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500) // Hiển thị splash screen trong 3 giây.
    }

    override fun onDestroy() {
        super.onDestroy()

        // Hủy bỏ handler nếu activity đang bị hủy.
        if (handler != null) {
            handler.removeCallbacksAndMessages(null)
        }
    }
}