package com.natife.forecast.forecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.api.weather.model.City
import com.natife.forecast.api.weather.model.CityResponse
import com.natife.forecast.api.weather.model.ForecastResponse
import com.natife.forecast.dagger.DaggerForecastComponent
import com.natife.forecast.forecast.adapters.HourlyForecast
import com.natife.forecast.forecast.data.ForecastPresentation
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastViewModel : ViewModel() {
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    var currentCity: City? = null
    var currentWeather: ForecastResponse? = null

    var model: ForecastModel = DaggerForecastComponent.create().getForecastModel()
    val forecastLiveData = MutableLiveData<ForecastPresentation>()
    val hourlyForecastLiveData = MutableLiveData<ArrayList<HourlyForecast>>()
    val dailyForecastLiveData = MutableLiveData<ArrayList<ForecastPresentation>>()

    fun getCity() {
        if (currentCity == null) {

            val cityData: Observable<CityResponse> = if (latitude == 0.0 && longitude == 0.0) {
                model.getCityData()
            } else {
                model.getCityData(latitude, longitude)
            }

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

    fun getUpdateCity(longitude: Double, latitude: Double) {
        currentCity = null
        this.latitude = latitude
        this.longitude = longitude
        getCity()
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

    fun setCoords(latitude: Double, longitude: Double) {

        this.latitude = latitude
        this.longitude = longitude
    }
}