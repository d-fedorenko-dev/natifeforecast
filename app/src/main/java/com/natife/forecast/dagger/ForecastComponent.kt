package com.natife.forecast.dagger

import com.natife.forecast.api.RetrofitServices
import com.natife.forecast.forecast.ForecastModel
import com.natife.forecast.forecast.data.ForecastRepository
import dagger.Component

@Component(modules = [ForecastModule::class])
interface ForecastComponent {
    fun getForecastModel(): ForecastModel
    fun getForecastRepository(): ForecastRepository
    fun getRetrofitService(): RetrofitServices

}