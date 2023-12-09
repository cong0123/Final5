package com.example.final5

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var textLight: TextView
    private lateinit var textRoomTemperature: TextView
    private lateinit var textRoomHumidity: TextView
    private lateinit var textBabyTemperature: TextView
    private lateinit var textBabyMotionSensor: TextView
    private lateinit var textBabyCries: TextView

    private val databaseReference = FirebaseDatabase.getInstance().getReference("sensorData")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Baby care")




        textLight = findViewById(R.id.textLight)
        textRoomTemperature = findViewById(R.id.textRoomTemperature)
        textRoomHumidity = findViewById(R.id.textRoomHumidity)
        textBabyTemperature = findViewById(R.id.textBabyTemperature)
        textBabyMotionSensor = findViewById(R.id.textBabyMotionSensor)
        textBabyCries = findViewById(R.id.textBabyCries)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lightValue = dataSnapshot.child("light").getValue(Long::class.java)
                val babyCries = dataSnapshot.child("babyCries").getValue(Int::class.java)
                val roomTemperature = dataSnapshot.child("roomTemperature").getValue(Double::class.java)
                val roomHumidity = dataSnapshot.child("roomHumidity").getValue(Long::class.java)
                val babyTemperature = dataSnapshot.child("babyTemperature").getValue(Double::class.java)
                val babyMotionSensor = dataSnapshot.child("babyMotionSensor").getValue(String::class.java)


                lightValue?.let {
                    val formattedLightValue = String.format("Light Value: %d Lux", lightValue)
                    textLight.text = formattedLightValue
                }

                babyCries?.let {
                    Log.d("MainActivity", "Baby Cries: $babyCries")
                    val babyCriesText = convertBabyCriesValueToString(babyCries)
                    textBabyCries.text = "Baby Cries: $babyCriesText"
                }


                roomTemperature?.let {
                    AlertManager.showRoomTemperatureAlert(applicationContext, it.toFloat())

                    val formattedRoomTemperature = String.format("Room Temperature: %.2f°C", roomTemperature)
                    updateFieldColor(textRoomTemperature, it.toFloat(), 26f, 29f)
                    textRoomTemperature.text = formattedRoomTemperature
                }

                babyTemperature?.let {
                    AlertManager.showBabyTemperatureAlert(applicationContext, it.toFloat())

                    val formattedBabyTemperature = String.format("Baby Temperature: %.2f°C", babyTemperature)
                    updateFieldColor(textBabyTemperature, it.toFloat(), 36.5f, 37.5f)
                    textBabyTemperature.text = formattedBabyTemperature
                }

                roomHumidity?.let {
                    AlertManager.showHumidityAlert(applicationContext, it.toFloat())

                    val formattedRoomHumidity = String.format("Room Humidity: %d%%", roomHumidity)
                    updateFieldColor(textRoomHumidity, it.toFloat(), 45f, 60f)
                    textRoomHumidity.text = formattedRoomHumidity
                }

                babyMotionSensor?.let {
                    textBabyMotionSensor.text = "Baby Pee: $babyMotionSensor"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })
    }

    private fun updateFieldColor(textView: TextView, value: Float, lowerLimit: Float, upperLimit: Float) {
        if (value < lowerLimit || value > upperLimit) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorWarning))
        } else {
            textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        }
    }

    private fun convertBabyCriesValueToString(value: Int): String {
        return when (value) {
            1 -> "No"
            2 -> "Sleep"
            3 -> "Hurt"
            4 -> "Burp"
            5 -> "Teething"
            6 -> "Uncomfortable"
            7 -> "Lonely"
            8 -> "Thirsty"
            9 -> "Hungry"
            10 -> "There is no reason for the baby to cry."
            else -> "Unknown"
        }
    }
}
