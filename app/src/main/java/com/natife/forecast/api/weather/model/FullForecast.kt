package com.natife.forecast.api.weather.model

data class ForecastResponse(
    val timezone: String,
    val timezone_offset: Int,
    val current: Forecast,
    val hourly: List<Forecast>,
    val daily: List<ForecastTemp>
)

data class Forecast(
    val dt: Int,
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val weather: List<Weather>,
)

data class ForecastTemp(
    val dt: Int,
    val temp: Temp,
    val pressure: Int,
    val humidity: Int,
    val wind_speed: Double,
    val wind_deg: Int,
    val weather: List<Weather>,
)

data class Temp(
    val day: Double,
    val night: Double,
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)




