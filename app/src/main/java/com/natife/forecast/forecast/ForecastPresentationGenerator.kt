package com.natife.forecast.forecast

import com.natife.forecast.R
import com.natife.forecast.api.model.City
import com.natife.forecast.api.model.ForecastResponse
import com.natife.forecast.forecast.adapters.DailyForecast
import com.natife.forecast.forecast.adapters.HourlyForecast
import com.natife.forecast.forecast.data.ForecastPresentation
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

class ForecastPresentationGenerator(
    private val city: City,
    private val forecast: ForecastResponse
) {
    var forecastPresentation: ForecastPresentation = ForecastPresentation()
    var hourlyForecastPresentation = ArrayList<HourlyForecast>()
    var dailyForecastPresentation = ArrayList<DailyForecast>()


    private val whiteWeatherIconMap = mapOf(
        "01d" to R.drawable.ic_white_day_bright,
        "02d" to R.drawable.ic_white_day_cloudy,
        "03d" to R.drawable.ic_white_day_cloudy,
        "04d" to R.drawable.ic_white_day_cloudy,
        "09d" to R.drawable.ic_white_day_shower,
        "10d" to R.drawable.ic_white_day_rain,
        "11d" to R.drawable.ic_white_day_thunder,
        "13d" to R.drawable.ic_white_day_rain,
        "50d" to R.drawable.ic_white_day_cloudy,
        "01n" to R.drawable.ic_white_night_bright,
        "02n" to R.drawable.ic_white_night_cloudy,
        "03n" to R.drawable.ic_white_night_cloudy,
        "04n" to R.drawable.ic_white_night_cloudy,
        "09n" to R.drawable.ic_white_night_shower,
        "10n" to R.drawable.ic_white_night_rain,
        "11n" to R.drawable.ic_white_night_thunder,
        "13n" to R.drawable.ic_white_night_rain,
        "50n" to R.drawable.ic_white_night_cloudy
    )
    private val blackWeatherIconMap = mapOf(
        "01d" to R.drawable.ic_black_day_bright,
        "02d" to R.drawable.ic_black_day_cloudy,
        "03d" to R.drawable.ic_black_day_cloudy,
        "04d" to R.drawable.ic_black_day_cloudy,
        "09d" to R.drawable.ic_black_day_shower,
        "10d" to R.drawable.ic_black_day_rain,
        "11d" to R.drawable.ic_black_day_thunder,
        "13d" to R.drawable.ic_black_day_rain,
        "50d" to R.drawable.ic_black_day_cloudy,
        "01n" to R.drawable.ic_black_night_bright,
        "02n" to R.drawable.ic_black_night_cloudy,
        "03n" to R.drawable.ic_black_night_cloudy,
        "04n" to R.drawable.ic_black_night_cloudy,
        "09n" to R.drawable.ic_black_night_shower,
        "10n" to R.drawable.ic_black_night_rain,
        "11n" to R.drawable.ic_black_night_thunder,
        "13n" to R.drawable.ic_black_night_rain,
        "50n" to R.drawable.ic_black_night_cloudy
    )

    init {
        generateMainForecast()
        generateHourlyForecast()
        generateDailyForecast()
    }

    private fun generateMainForecast() {
        forecastPresentation.city = city.name.toString()

        val date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(forecast.current.dt * 1000L),
            ZoneId.systemDefault()
        )
        forecastPresentation.date = date.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.FULL
            )
        )

        forecastPresentation.hum = forecast.current.humidity.toString() + " %"
        forecastPresentation.temp =
            forecast.current.temp.roundToInt().toString() + "°"
        forecastPresentation.wind =
            forecast.current.wind_speed.roundToInt().toString() + " м/сек"

        forecastPresentation.windIcon = getWindIcon(forecast.current.wind_deg)
        forecastPresentation.weatherIcon = whiteWeatherIconMap.getOrDefault(
            forecast.current.weather[0].icon,
            R.drawable.ic_white_day_bright
        )

    }

    private fun getWindIcon(windDeg: Int): Int {
        when (windDeg) {
            in 0..23 -> return R.drawable.ic_icon_wind_n
            in 23..67 -> return R.drawable.ic_icon_wind_ne
            in 67..113 -> return R.drawable.ic_icon_wind_e
            in 113..157 -> return R.drawable.ic_icon_wind_se
            in 157..203 -> return R.drawable.ic_icon_wind_s
            in 203..247 -> return R.drawable.ic_icon_wind_ws
            in 247..293 -> return R.drawable.ic_icon_wind_w
            in 293..337 -> return R.drawable.ic_icon_wind_wn
            in 337..380 -> return R.drawable.ic_icon_wind_n
            else -> {
                return R.drawable.ic_icon_wind_n
            }
        }
    }


    private fun generateDailyForecast() {

        for (forecastDay in forecast.daily) {

            val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(forecast.current.dt * 1000L),
                ZoneId.systemDefault()
            )
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern(
                    "E"
                )
            )

            dailyForecastPresentation.add(
                DailyForecast(
                    formattedDate.capitalize(),
                    forecastDay.temp.day.roundToInt()
                        .toString() + "°/" + forecastDay.temp.night.roundToInt().toString() + "°",
                    blackWeatherIconMap.getOrDefault(
                        forecastDay.weather[0].icon,
                        R.drawable.ic_black_day_bright
                    )
                )
            )
        }
    }

    private fun generateHourlyForecast() {
        for (i in 1..24) {
            val forecastHour = forecast.hourly[i]
            val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(forecastHour.dt * 1000L),
                ZoneId.systemDefault()
            )
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern(
                    "HH:00"
                )
            )

            hourlyForecastPresentation.add(
                HourlyForecast(
                    formattedDate,
                    forecastHour.temp.roundToInt().toString() + "°",
                    whiteWeatherIconMap.getOrDefault(
                        forecastHour.weather[0].icon,
                        R.drawable.ic_white_day_bright
                    )
                )
            )
        }
    }


}