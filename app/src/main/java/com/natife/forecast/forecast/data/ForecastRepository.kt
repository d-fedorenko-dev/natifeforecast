package com.natife.forecast.forecast.data

import com.natife.forecast.api.weather.ForecastRetrofitServices
import com.natife.forecast.api.weather.model.City
import com.natife.forecast.api.weather.model.CityResponse
import com.natife.forecast.api.weather.model.ForecastResponse
import io.reactivex.Observable

import javax.inject.Inject

class ForecastRepository @Inject constructor(var mService: ForecastRetrofitServices) {
    fun getCity(): Observable<CityResponse> {
        return mService.getCity("Киев", "0b45c2817e246e44f31b50b72d78fd6f")
    }

    fun getCity(lat: Double, lon: Double): Observable<CityResponse> {
        return mService.getCity(lat, lon, "0b45c2817e246e44f31b50b72d78fd6f")
    }

    fun getForecast(city: City): Observable<ForecastResponse> {
        return mService.getForecast(city.lat()!!, city.lon()!!, "0b45c2817e246e44f31b50b72d78fd6f")
    }

}