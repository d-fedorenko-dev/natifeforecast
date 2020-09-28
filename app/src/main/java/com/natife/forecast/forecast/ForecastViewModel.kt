package com.natife.forecast.forecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.api.model.City
import com.natife.forecast.api.model.ForecastResponse
import com.natife.forecast.dagger.DaggerForecastComponent
import com.natife.forecast.forecast.adapters.DailyForecast
import com.natife.forecast.forecast.adapters.HourlyForecast
import com.natife.forecast.forecast.data.ForecastPresentation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastViewModel : ViewModel() {
    var currentCity: City? = null
    var currentWeather: ForecastResponse? = null

    var model: ForecastModel = DaggerForecastComponent.create().getForecastModel()
    val forecastLiveData = MutableLiveData<ForecastPresentation>()
    val hourlyForecastLiveData = MutableLiveData<ArrayList<HourlyForecast>>()
    val dailyForecastLiveData = MutableLiveData<ArrayList<DailyForecast>>()

    init {
        getCity()

//        getHourlyForecast()
//        getDailyForecast()
    }

    private fun getCity() {
        if (currentCity == null) {
            val cityData = model.getCityData()
            val subscribe = cityData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        run {
                            currentCity = response.list?.get(0)
                            getForecast()
                        }
                    },
                    { t ->
                        run {
                            Log.e("GET", "Error on getCityData", t)
                        }
                    })
        }
    }

    private fun getForecast() {
        if (currentCity != null) {
            val forecast = model.getForecast(currentCity!!)
            val subscribe = forecast.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        run {
                            currentWeather = response
                            showData()
//
//                            val forecastPresentation = ForecastPresentation()
//                            forecastPresentation.city = currentCity?.name.toString()
//                            //TODO make DaTEFORMAT
//                            val date = ofInstant(
//                                Instant.ofEpochMilli(response.current.dt * 1000L),
//                                ZoneId.systemDefault()
//                            )
//
//                            forecastPresentation.date = date.format(
//                                DateTimeFormatter.ofLocalizedDate(
//                                    FormatStyle.MEDIUM
//                                )
//                            )
//                            forecastPresentation.hum = response.current.humidity.toString() + " %"
//                            //TODO c deg
//                            forecastPresentation.temp =
//                                response.current.temp.roundToInt().toString() + "°"
//                            //TODO ms on other string
//                            forecastPresentation.wind =
//                                response.current.wind_speed.roundToInt().toString() + " м/сек"
//
//                            //TODO Select picture
//                            forecastLiveData.postValue(forecastPresentation)
//                            //TODO parse hourly and daily
//

                        }
                    },
                    { t ->
                        run {
                            Log.e("GET", "Error on getForecast", t)
                        }
                    })
        }
    }

    private fun showData() {
        val fpg = ForecastPresentationGenerator(currentCity!!, currentWeather!!)
        forecastLiveData.postValue(fpg.forecastPresentation)
        hourlyForecastLiveData.postValue(fpg.hourlyForecastPresentation)
        dailyForecastLiveData.postValue(fpg.dailyForecastPresentation)
    }
}