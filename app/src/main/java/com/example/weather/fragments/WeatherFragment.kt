package com.example.weather.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.weather.R
import com.example.weather.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherFragment : Fragment() {

    private lateinit var cityTV: TextView
    private lateinit var temperatureTV: TextView
    private lateinit var weatherIV: ImageView
    private lateinit var windDegreeTV: TextView
    private lateinit var windSpeedTV: TextView
    private lateinit var pressureTV: TextView
    private lateinit var maxMinTV: TextView
    private var city: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString("city")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityTV = view.findViewById(R.id.cityTV)
        temperatureTV = view.findViewById(R.id.temperatureTV)
        weatherIV = view.findViewById(R.id.weatherIV)
        windDegreeTV = view.findViewById(R.id.windDegreeTV)
        windSpeedTV = view.findViewById(R.id.windSpeedTV)
        pressureTV = view.findViewById(R.id.pressureTV)
        maxMinTV = view.findViewById(R.id.maxMinTV)

        getCurrentWeather()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    city!!,
                    "metric",
                    requireContext().getString(R.string.api_key)
                )
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return@launch
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Ошибка ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    val deg = data!!.wind.deg
                    var degree = ""
                    when (deg) {
                        in 338..360 -> degree = "С"
                        in 0..22 -> degree = "С"
                        in 23..67 -> degree = "СВ"
                        in 68..112 -> degree = "З"
                        in 113..157 -> degree = "ЮВ"
                        in 158..202 -> degree = "Ю"
                        in 203..247 -> degree = "ЮЗ"
                        in 248..292 -> degree = "З"
                        in 293..337 -> degree = "СЗ"
                    }
                    cityTV.text = data.name
                    temperatureTV.text = "${data.main.temp}°"
                    maxMinTV.text = "Макс: ${data.main.temp_max}°, Мин: ${data.main.temp_min}°"
                    windDegreeTV.text = "${data.wind.deg}° $degree"
                    windSpeedTV.text = "${data.wind.speed} м/с"
                    val convertPressure = (data.main.pressure / 1.33).toInt().toString()
                    pressureTV.text = "$convertPressure мм рт.ст."
                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(weatherIV)
                }
            }
        }
    }
}