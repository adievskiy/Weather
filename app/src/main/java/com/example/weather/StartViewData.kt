package com.example.weather

import java.io.Serializable

class StartViewData(val image: Int, val description: String, val check: Boolean) : Serializable {
    companion object {
        val startList = mutableListOf(
            StartViewData(
                R.drawable.weather02,
                "Совершенно новое и абсолютно невиданное до этого момента\nприложение ПОГОДА!!!",
                false
            ),
            StartViewData(
                R.drawable.weather01,
                "Здесь даже надо вручную вводить город, перед тем как её увидеть!",
                false
            ),
            StartViewData(
                R.drawable.weather02,
                "Потому что я никак не могу получить данные о местоположении(((",
                true
            ),
        )
    }
}
