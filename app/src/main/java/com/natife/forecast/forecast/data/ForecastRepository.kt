package com.natife.forecast.forecast.data

import com.natife.forecast.api.RetrofitServices
import com.natife.forecast.api.model.City
import com.natife.forecast.api.model.CityResponse
import com.natife.forecast.api.model.ForecastResponse
import io.reactivex.Observable

import javax.inject.Inject

class ForecastRepository @Inject constructor(var mService: RetrofitServices) {
    fun getCity(): Observable<CityResponse> {
        //TODO hardcoded appid
        return mService.getCity("Запорожье", "0b45c2817e246e44f31b50b72d78fd6f")
    }

    fun getForecast(city: City): Observable<ForecastResponse> {
        //TODO hardcoded appid
        return mService.getForecast(city.lat()!!, city.lon()!!, "0b45c2817e246e44f31b50b72d78fd6f")
    }

}