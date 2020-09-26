package com.natife.forecast.forecast

import com.natife.forecast.R


class IconMapping {
    companion object {
        const val BRIGHT = 1
        fun getResourceById(id: Int, light: Boolean = true): Int {
            //TODO Make mapping?
            //val map = mapOf(1 to "one", 3 to "three", 9 to "nine")
            return if (light)
                R.drawable.ic_white_day_bright
            else R.drawable.ic_black_day_rain
        }

    }
}