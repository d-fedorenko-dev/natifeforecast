package com.natife.forecast.forecast

import com.natife.forecast.api.model.City
import com.natife.forecast.api.model.CityResponse
import com.natife.forecast.api.model.ForecastResponse
import com.natife.forecast.forecast.data.ForecastRepository
import io.reactivex.Observable
import javax.inject.Inject

class ForecastModel @Inject constructor(
    private var forecastRepository: ForecastRepository
) {
    fun getForecast(city: City): Observable<ForecastResponse> = forecastRepository.getForecast(city)
    fun getCityData(): Observable<CityResponse> = forecastRepository.getCity()
    fun getCityData(lat: Double, lon: Double): Observable<CityResponse> =
        forecastRepository.getCity(lat, lon)
}
