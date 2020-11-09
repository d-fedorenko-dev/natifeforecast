package com.natife.forecast.location

import com.natife.forecast.api.search.model.Search
import com.natife.forecast.location.data.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchModel @Inject constructor(
    private var searchRepository: SearchRepository
) {
    fun getList(search: String): Observable<List<Search>> = searchRepository.getList(search)
}