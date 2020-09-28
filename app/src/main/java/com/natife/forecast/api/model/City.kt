package com.natife.forecast.api.model

data class CityResponse(
    var list: ArrayList<City>? = null
)

data class City(
    var name: String? = null,
    var coord: Cords? = null
) {
    fun lat(): String? {
        return coord?.lat
    }

    fun lon(): String? {
        return coord?.lon
    }
}

data class Cords(
    var lat: String? = null,
    var lon: String? = null
)