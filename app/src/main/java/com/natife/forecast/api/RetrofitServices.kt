package com.natife.forecast.api

import com.natife.forecast.api.model.CityResponse
import com.natife.forecast.api.model.ForecastResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("find?lang=ru")
    fun getCity(
        @Query("q") cityName: String,
        @Query("appid") appId: String
    ): Observable<CityResponse>

    @GET("find?lang=ru")
    fun getCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String
    ): Observable<CityResponse>

    @GET("onecall?lang=ru&exclude=minutely&units=metric")
    fun getForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String
    ): Observable<ForecastResponse>
}