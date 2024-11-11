package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.weather.R

class CityFragment : Fragment() {
    private lateinit var inputCity: EditText
    private lateinit var startBTN: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputCity = view.findViewById(R.id.inputCity)
        startBTN = view.findViewById(R.id.startBTN)

        startBTN.setOnClickListener {
            val weatherFragment = WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString("city", inputCity.text.toString())
                }
            }
            requireFragmentManager().beginTransaction()
                .replace(R.id.weather, weatherFragment)
                .commit()
        }
    }
}