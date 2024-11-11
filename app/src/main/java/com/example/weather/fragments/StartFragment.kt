package com.example.weather.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.weather.R
import com.example.weather.StartViewData
import com.example.weather.WeatherActivity

class StartFragment : Fragment() {

    private lateinit var imageIV: ImageView
    private lateinit var descriptionTV: TextView
    private lateinit var goToWeatherBTN: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageIV = view.findViewById(R.id.imageIV)
        descriptionTV = view.findViewById(R.id.descriptionTV)
        goToWeatherBTN = view.findViewById(R.id.goToWeatherBTN)

        val startList = arguments?.getSerializable("start") as StartViewData
        imageIV.setImageResource(startList.image)
        descriptionTV.text = startList.description
        if (startList.check) {
            goToWeatherBTN.visibility = View.VISIBLE
            goToWeatherBTN.setOnClickListener {
                startActivity(Intent(activity, WeatherActivity::class.java))
            }
        }
    }
}