package com.natife.forecast.location.data

import com.natife.forecast.api.search.SearchRetrofitServices
import com.natife.forecast.api.search.model.Search
import io.reactivex.Observable
import javax.inject.Inject


class SearchRepository @Inject constructor(var mService: SearchRetrofitServices) {
    fun getList(search: String): Observable<List<Search>> {
        return mService.getList(search)
    }
}