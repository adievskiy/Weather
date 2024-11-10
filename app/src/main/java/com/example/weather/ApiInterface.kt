package com.example.weather

import com.example.weather.models.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appId") apiKey: String,
    ): Response<CurrentWeather>
}