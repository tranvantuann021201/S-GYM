package com.example.s_gym.until

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.content.BroadcastReceiver
import com.example.s_gym.MainActivity
import com.example.s_gym.R

class NotifyRemindFitnessBR : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)

            val notificationBuilder = NotificationCompat.Builder(context, "FITNESS_CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_timer_24)
                .setContentTitle("Nhắc nhở luyện tập")
                .setContentText("Đừng quên luyện tập hàng ngày bạn nhé!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(0, notificationBuilder.build())
        }
    }
}

