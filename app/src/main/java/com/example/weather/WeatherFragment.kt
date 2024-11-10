package com.example.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class WeatherFragment : Fragment() {

    private lateinit var cityTV: TextView
    private lateinit var temperatureTV: TextView
    private lateinit var weatherIV: ImageView
    private lateinit var windDegreeTV: TextView
    private lateinit var windSpeedTV: TextView
    private lateinit var pressureTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }
}