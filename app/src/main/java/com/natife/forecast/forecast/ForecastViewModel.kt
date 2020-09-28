package com.natife.forecast.forecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.api.model.City
import com.natife.forecast.api.model.ForecastResponse
import com.natife.forecast.dagger.DaggerForecastComponent
import com.natife.forecast.forecast.data.ForecastPresentation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.Instant
import java.time.LocalDateTime.ofInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

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
                            val forecastPresentation = ForecastPresentation()
                            forecastPresentation.city = currentCity?.name.toString()
                            forecastLiveData.postValue(forecastPresentation)

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
                            val forecastPresentation = ForecastPresentation()

                            forecastPresentation.city = currentCity?.name.toString()

                            //TODO make DaTEFORMAT
                            val date = ofInstant(
                                Instant.ofEpochMilli(response.current.dt * 1000L),
                                ZoneId.systemDefault()
                            )

                            forecastPresentation.date = date.format(
                                DateTimeFormatter.ofLocalizedDate(
                                    FormatStyle.MEDIUM
                                )
                            )
                            forecastPresentation.hum = response.current.humidity.toString() + " %"
                            //TODO c deg
                            forecastPresentation.temp =
                                response.current.temp.roundToInt().toString() + "°"
                            //TODO ms on other string
                            forecastPresentation.wind =
                                response.current.wind_speed.roundToInt().toString() + " м/сек"

                            //TODO Select picture
                            forecastLiveData.postValue(forecastPresentation)
                            //TODO parse hourly and daily


                        }
                    },
                    { t ->
                        run {
                            Log.e("GET", "Error on getForecast", t)
                        }
                    })
        }
    }

    private fun getHourlyForecast() {
        val hourlyForecastPresentation = ArrayList<HourlyForecast>()
        //TODO add get from mvvvm
        hourlyForecastPresentation.add(
            HourlyForecast(
                "12:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "13:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "14:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "15:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "16:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "17:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "18:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "19:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "10:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "21:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "22:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "23:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "24:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "00:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "01:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "02:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "03:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "04:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "05:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "06:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "07:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "08:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "09:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "10:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastPresentation.add(
            HourlyForecast(
                "11:30",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        hourlyForecastLiveData.postValue(hourlyForecastPresentation)
    }

    private fun getDailyForecast() {
        val dailyForecastPresentation = ArrayList<DailyForecast>()
        //TODO add get from mvvvm
        dailyForecastPresentation.add(
            DailyForecast(
                "ПН",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "ВТ",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "СР",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "ЧТ",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "ПТ",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "СБ",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "ВС",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastPresentation.add(
            DailyForecast(
                "ПН",
                "42 C",
                IconMapping.BRIGHT
            )
        )
        dailyForecastLiveData.postValue(dailyForecastPresentation)
    }
}