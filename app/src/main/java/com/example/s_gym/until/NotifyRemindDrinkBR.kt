package com.example.s_gym.until

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.s_gym.MainActivity
import com.example.s_gym.R

class NotifyRemindDrinkBR: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
            == PackageManager.PERMISSION_GRANTED && intent.action == "android.intent.action.BOOT_COMPLETED"
        ) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)

            val notificationBuilder = NotificationCompat.Builder(context, "WATER_CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_local_drink_24)
                .setContentTitle("S-GYM")
                .setContentText("Uống nước đều đặn, sức khỏe dồi dào !!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(1, notificationBuilder.build())
            }
        }
    }
}