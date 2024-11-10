package com.example.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weather.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var cityTV: TextView
    private lateinit var temperatureTV: TextView
    private lateinit var weatherIV: ImageView
    private lateinit var windDegreeTV: TextView
    private lateinit var windSpeedTV: TextView
    private lateinit var pressureTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cityTV = findViewById(R.id.cityTV)
        temperatureTV = findViewById(R.id.temperatureTV)
        weatherIV = findViewById(R.id.weatherIV)
        windDegreeTV = findViewById(R.id.windDegreeTV)
        windSpeedTV = findViewById(R.id.windSpeedTV)
        pressureTV = findViewById(R.id.pressureTV)

        getCurrentWeather()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    "Тольятти",
                    "metric",
                    applicationContext.getString(R.string.api_key)
                )
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Ошибка ${e.message}", Toast.LENGTH_LONG).show()
                return@launch
            } catch (e:HttpException) {
                Toast.makeText(this@MainActivity, "Ошибка ${e.message}", Toast.LENGTH_LONG).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    cityTV.text = data!!.name
                    temperatureTV.text = "${data.main.temp}°C"
                    windDegreeTV.text = "${data.wind.deg}"
                    windSpeedTV.text = "${data.wind.speed} м/с"
                    val convertPressure = (data.main.pressure / 1.33)
                    pressureTV.text = convertPressure.toString()
                    val iconId = data.weather[0].icon
                    val imageUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"
                    Picasso.get().load(imageUrl).into(weatherIV)
                }
            }
        }
    }
}