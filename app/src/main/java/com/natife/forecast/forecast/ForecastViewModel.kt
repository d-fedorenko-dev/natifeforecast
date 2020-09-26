package com.natife.forecast.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.dagger.DaggerForecastComponent
import com.natife.forecast.forecast.data.ForecastPresentation

class ForecastViewModel : ViewModel() {
    var model: ForecastModel = DaggerForecastComponent.create().getForecastModel()
    val forecastLiveData = MutableLiveData<ForecastPresentation>()
    val hourlyForecastLiveData = MutableLiveData<ArrayList<HourlyForecast>>()
    val dailyForecastLiveData = MutableLiveData<ArrayList<DailyForecast>>()

    init {
        getForecast()
        getHourlyForecast()
        getDailyForecast()
    }

    private fun getForecast() {
        val forecast = model.getForecast()
        val forecastPresentation = ForecastPresentation()
        forecastPresentation.city = forecast.city
        forecastPresentation.date = forecast.date
        forecastPresentation.hum = forecast.hum + " %"
        forecastPresentation.temp = forecast.temp + " C"
        forecastPresentation.wind = forecast.wind + " m/s"

        forecastLiveData.postValue(forecastPresentation)
    }

    private fun getHourlyForecast() {
        val hourlyForecastPresentation = ArrayList<HourlyForecast>()
        //TODO add get from mvvvm
        hourlyForecastPresentation.add(HourlyForecast("12:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("13:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("14:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("15:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("16:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("17:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("18:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("19:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("10:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("21:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("22:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("23:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("24:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("00:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("01:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("02:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("03:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("04:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("05:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("06:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("07:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("08:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("09:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("10:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastPresentation.add(HourlyForecast("11:30", "42 C", IconMapping.BRIGHT))
        hourlyForecastLiveData.postValue(hourlyForecastPresentation)
    }

    private fun getDailyForecast() {
        val dailyForecastPresentation = ArrayList<DailyForecast>()
        //TODO add get from mvvvm
        dailyForecastPresentation.add(DailyForecast("ПН", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("ВТ", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("СР", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("ЧТ", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("ПТ", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("СБ", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("ВС", "42 C", IconMapping.BRIGHT))
        dailyForecastPresentation.add(DailyForecast("ПН", "42 C", IconMapping.BRIGHT))
        dailyForecastLiveData.postValue(dailyForecastPresentation)
    }
}