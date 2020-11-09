package com.natife.forecast.dagger

import com.natife.forecast.api.search.SearchRetrofitClient
import com.natife.forecast.api.search.SearchRetrofitServices
import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    fun retrofit(): SearchRetrofitServices {
        val BASE_URL = "https://nominatim.openstreetmap.org/search/"
        return SearchRetrofitClient.get(BASE_URL)
            .create(SearchRetrofitServices::class.java)
    }
}