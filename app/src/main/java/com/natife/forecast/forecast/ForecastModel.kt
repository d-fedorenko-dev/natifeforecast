package com.natife.forecast.forecast

import com.natife.forecast.forecast.data.Forecast
import com.natife.forecast.forecast.data.ForecastRepository

class ForecastModel {
    private val forecastRepository = ForecastRepository()
    fun getForecast(): Forecast = forecastRepository.getForecast()

}
