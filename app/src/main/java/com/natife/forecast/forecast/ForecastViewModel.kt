package com.natife.forecast.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.forecast.data.ForecastPresentation

class ForecastViewModel : ViewModel() {
    private val model = ForecastModel()
    val forecastLiveData = MutableLiveData<ForecastPresentation>()

    init {
        getForecast()
    }

    private fun getForecast() {
        val forecast = model.getForecast()
        val forecastPresentation = ForecastPresentation()
        forecastPresentation.city = forecast.city
        forecastPresentation.date = forecast.date
        forecastPresentation.hum = forecast.hum + " %"
        forecastPresentation.temp = forecast.temp + " C"
        forecastPresentation.wind = forecast.wind + " m/s"

        forecastLiveData.postValue(forecastPresentation)
    }
}