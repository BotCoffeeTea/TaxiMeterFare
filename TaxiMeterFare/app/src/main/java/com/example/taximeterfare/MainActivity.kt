package com.example.taximeterfare

import android.content.Intent

import android.os.Bundle

import android.widget.Button

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var distanceTextView: TextView

    private lateinit var durationTextView: TextView

    private lateinit var fareTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        distanceTextView = findViewById(R.id.distance_text)

        durationTextView = findViewById(R.id.duration_text)

        fareTextView = findViewById(R.id.fare_text)

        val fareSettingButton = findViewById<Button>(R.id.fare_setting_button)

        fareSettingButton.setOnClickListener {

            val intent = Intent(this, FareSettingActivity::class.java)

            startActivity(intent)

        }

    }

    override fun onResume() {

        super.onResume()

        val distance = 10.0 // replace with actual distance

        val duration = 15 // replace with actual duration

        val fare = FareCalculator(this).calculateFare(distance, duration)

        distanceTextView.text = "Distance: $distance km"

        durationTextView.text = "Duration: $duration mins"

        fareTextView.text = "Fare: $fare VND"

    }

}