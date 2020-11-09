package com.natife.forecast.api.search.model

class SearchResponse() {
    var list: ArrayList<Search>? = null
}

class Search {
    var lat: String = "0"
    var lon: String = "0"
    var display_name: String = ""
}
