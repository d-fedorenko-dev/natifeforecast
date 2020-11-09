package com.natife.forecast.api.search

import com.natife.forecast.api.search.model.Search
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitServices {
    @GET("search?format=json&addressdetails=1")
    fun getList(
        @Query("q") cityName: String,
    ): Observable<List<Search>>
}