package com.natife.forecast.dagger

import com.natife.forecast.api.RetrofitClient
import com.natife.forecast.api.RetrofitServices
import com.natife.forecast.forecast.ForecastModel
import com.natife.forecast.forecast.data.ForecastRepository
import dagger.Module
import dagger.Provides

@Module
class ForecastModule {
    @Provides
    fun forecastRepository(retrofit: RetrofitServices): ForecastRepository {
        return ForecastRepository(retrofit)
    }

    @Provides
    fun forecastModel(forecastRepository: ForecastRepository): ForecastModel {
        return ForecastModel(forecastRepository)
    }

    @Provides
    fun retrofit(): RetrofitServices {
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        //TODO make global
        return RetrofitClient.get(BASE_URL)
            .create(RetrofitServices::class.java)
    }
}
