package com.natife.forecast.location.data

import com.natife.forecast.api.search.model.Search

class SearchPresentation(search: Search) {
    var lon: String = "0.0"
    var lat: String = "0.0"
    var name: String = ""

    init {
        name = search.display_name
        lat = search.lat
        lon = search.lon
    }

}