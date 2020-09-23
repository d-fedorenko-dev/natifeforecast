package com.natife.forecast.dagger

import com.natife.forecast.forecast.ForecastModel
import com.natife.forecast.forecast.data.ForecastRepository
import dagger.Module
import dagger.Provides

@Module
class ForecastModule {
    @Provides
    fun forecastRepository(): ForecastRepository {
        return ForecastRepository()
    }

    @Provides
    fun forecastModel(forecastRepository: ForecastRepository): ForecastModel {
        return ForecastModel(forecastRepository)
    }
}
