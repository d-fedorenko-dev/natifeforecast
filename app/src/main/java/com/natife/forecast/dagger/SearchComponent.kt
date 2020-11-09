package com.natife.forecast.dagger

import com.natife.forecast.api.search.SearchRetrofitServices
import com.natife.forecast.location.SearchModel
import com.natife.forecast.location.data.SearchRepository
import dagger.Component

@Component(modules = [SearchModule::class])
interface SearchComponent {
    fun getSearchModel(): SearchModel
    fun getSearchRepository(): SearchRepository
    fun getRetrofitService(): SearchRetrofitServices

}