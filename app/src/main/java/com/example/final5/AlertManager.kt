package com.example.final5

import android.content.Context
import android.util.Log
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

object AlertManager {

    fun showRoomTemperatureAlert(context: Context, temperature: Float) {
        // Xử lý cảnh báo cho nhiệt độ phòng
        Log.d("AlertManager", "Checking room temperature: $temperature")
        if (temperature < 25 || temperature > 29) {
            Log.d("AlertManager", "Room Temperature Alert: $temperature")
            showNotification(context, "Room Temperature Alert", "Room Temperature is outside the normal range.")
        } else {
            Log.d("AlertManager", "Room Temperature is within the normal range.")
        }
    }

    fun showBabyTemperatureAlert(context: Context, temperature: Float) {
        // Xử lý cảnh báo cho nhiệt độ của bé
        Log.d("AlertManager", "Checking baby temperature: $temperature")
        if (temperature < 36 || temperature > 38) {
            Log.d("AlertManager", "Baby Temperature Alert: $temperature")
            showNotification(context, "Baby Temperature Alert", "Baby Temperature is outside the normal range.")
        } else {
            Log.d("AlertManager", "Baby Temperature is within the normal range.")
        }
    }

    fun showHumidityAlert(context: Context, humidity: Float) {
        // Xử lý cảnh báo cho độ ẩm
        Log.d("AlertManager", "Checking humidity: $humidity")
        if (humidity < 45 || humidity > 60) {
            Log.d("AlertManager", "Humidity Alert: $humidity")
            showNotification(context, "Humidity Alert", "Humidity is outside the normal range.")
        } else {
            Log.d("AlertManager", "Humidity is within the normal range.")
        }
    }

    private fun showNotification(context: Context, title: String, content: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo một Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelName = "Default Channel"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Tạo Notification
        val notification = Notification.Builder(context, "default_channel_id")
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .build()

        // Hiển thị Notification
        notificationManager.notify(1, notification)
    }
}
