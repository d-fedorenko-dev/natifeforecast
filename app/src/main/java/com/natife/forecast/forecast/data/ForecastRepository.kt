package com.natife.forecast.forecast.data

import javax.inject.Inject

class ForecastRepository @Inject constructor() {

    fun getForecast(): Forecast {
        val forecast = Forecast()
        //make some operations
        forecast.temp = "42"
        return forecast
    }

}