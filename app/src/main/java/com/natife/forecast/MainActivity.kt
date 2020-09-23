package com.natife.forecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        DaggerForecastComponent.builder().forecastModule(ForecastModule())
//            .build()

        setContentView(R.layout.activity_main)
    }
}