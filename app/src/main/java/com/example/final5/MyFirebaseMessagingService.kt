package com.example.final5

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (remoteMessage.data.containsKey("roomTemperature")) {
                val roomTemperature = remoteMessage.data["roomTemperature"]?.toFloatOrNull()
                roomTemperature?.let {
                    Log.d(TAG, "Room Temperature from Firebase: $roomTemperature")
                    AlertManager.showRoomTemperatureAlert(applicationContext, it)
                }
            }

            if (remoteMessage.data.containsKey("babyTemperature")) {
                val babyTemperature = remoteMessage.data["babyTemperature"]?.toFloatOrNull()
                babyTemperature?.let {
                    Log.d(TAG, "Baby Temperature from Firebase: $babyTemperature")
                    AlertManager.showBabyTemperatureAlert(applicationContext, it)
                }
            }

            if (remoteMessage.data.containsKey("humidity")) {
                val humidity = remoteMessage.data["humidity"]?.toFloatOrNull()
                humidity?.let {
                    Log.d(TAG, "Humidity from Firebase: $humidity")
                    AlertManager.showHumidityAlert(applicationContext, it)
                }
            }
            if (remoteMessage.data.containsKey("babyCries")) {
                val babyCriesValue = remoteMessage.data["babyCries"]?.toIntOrNull()
                babyCriesValue?.let {
                    Log.d(TAG, "Baby Cries from Firebase: $babyCriesValue")
                    val babyCriesText = convertBabyCriesValueToString(it)
                }
            }

        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    private fun convertBabyCriesValueToString(value: Int): String {
        return when (value) {
            1 -> "No"
            2 -> "Sleep"
            3 -> "Hurt"
            4 -> "Belching"
            5 -> "Teething"
            6 -> "Uncomfortable"
            7 -> "Lonely"
            8 -> "Thirsty"
            9 -> "Hungry"
            10 -> "Nothing"
            else -> "Unknown"
        }
    }
}



