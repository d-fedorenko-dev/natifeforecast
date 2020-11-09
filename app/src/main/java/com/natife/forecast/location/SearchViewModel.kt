package com.natife.forecast.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.natife.forecast.api.search.model.Search
import com.natife.forecast.dagger.DaggerSearchComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    var model: SearchModel = DaggerSearchComponent.create().getSearchModel()

    val searchLiveData = MutableLiveData<ArrayList<Search>>()

    fun getSearchList(search: String) {
        val list = model.getList(search)
        val subscribe = list.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    run {
                        showData(response as ArrayList<Search>?)
                    }
                },
                { t ->
                    run {
                        Log.e("GET", "Error on getCityData", t)
                    }
                })
    }


    private fun showData(list: ArrayList<Search>?) {
        searchLiveData.postValue(list)

    }


}
