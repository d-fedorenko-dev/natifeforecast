package com.natife.forecast.dagger

import com.natife.forecast.api.weather.ForecastRetrofitClient
import com.natife.forecast.api.weather.ForecastRetrofitServices
import com.natife.forecast.forecast.ForecastModel
import com.natife.forecast.forecast.data.ForecastRepository
import dagger.Module
import dagger.Provides

@Module
class ForecastModule {
    @Provides
    fun forecastRepository(retrofit: ForecastRetrofitServices): ForecastRepository {
        return ForecastRepository(retrofit)
    }

    @Provides
    fun forecastModel(forecastRepository: ForecastRepository): ForecastModel {
        return ForecastModel(forecastRepository)
    }

    @Provides
    fun retrofit(): ForecastRetrofitServices {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        return ForecastRetrofitClient.get(BASE_URL)
            .create(ForecastRetrofitServices::class.java)
    }
}
