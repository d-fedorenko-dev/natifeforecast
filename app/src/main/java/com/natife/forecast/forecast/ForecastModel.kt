package com.natife.forecast.forecast

import com.natife.forecast.forecast.data.Forecast
import com.natife.forecast.forecast.data.ForecastRepository
import javax.inject.Inject

class ForecastModel @Inject constructor(
    private var forecastRepository: ForecastRepository
) {
    fun getForecast(): Forecast = forecastRepository.getForecast()
}
