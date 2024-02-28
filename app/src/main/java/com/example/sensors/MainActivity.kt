package com.example.sensors

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener {
    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    lateinit var image: ImageView
    lateinit var backgroundMain: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        image = findViewById<ImageView>(R.id.ivDisplayImage)
        backgroundMain = findViewById<ConstraintLayout>(R.id.conLayBackground)

        image.visibility = View.INVISIBLE
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)


    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event != null) {
                Log.d(TAG, "onSensorChanged: " + event.values[0])
            }
            if (event!!.values[0] < 30) {
                image.visibility = View.INVISIBLE
                backgroundMain.setBackgroundColor(resources.getColor(R.color.black))
            } else {
                image.visibility = View.VISIBLE
                backgroundMain.setBackgroundColor(resources.getColor(R.color._01c7e8))
            }
        } catch (e: IOException) {
            Log.d(TAG, "onSensorChanged : ${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}