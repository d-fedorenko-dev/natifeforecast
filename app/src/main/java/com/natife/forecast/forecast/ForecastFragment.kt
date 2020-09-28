package com.natife.forecast.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.natife.forecast.R
import kotlinx.android.synthetic.main.forecast_fragment.*

class ForecastFragment : Fragment() {
    private val viewModel: ForecastViewModel by activityViewModels()

    private lateinit var hourlyLinearLayoutManager: LinearLayoutManager
    private lateinit var dailyLinearLayoutManager: LinearLayoutManager

    private lateinit var hourlyAdapter: HourlyListAdapter
    private lateinit var dailyAdapter: DailyListAdapter

//    private val lastVisibleItemPosition: Int
//        get() = hourlyLinearLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentLocation.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_locationFragment)
        }

        hourlyLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hourlyList.recycledViewPool.setMaxRecycledViews(0, 0)
        hourlyList.setItemViewCacheSize(24)
        hourlyList.layoutManager = hourlyLinearLayoutManager

        dailyLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dailyList.recycledViewPool.setMaxRecycledViews(0, 0)
        dailyList.setItemViewCacheSize(12)
        dailyList.layoutManager = dailyLinearLayoutManager

        observeCity()
        //blank
        observeForecast()
        observeHourlyForecast()
        observeDailyForecast()
    }

    private fun observeForecast() {
        viewModel.forecastLiveData.observe(viewLifecycleOwner, { forecast ->
            run {
                temp.text = forecast.temp
                humidity.text = forecast.hum
                city.text = forecast.city
                wind.text = forecast.wind
                date.text = forecast.date
            }

        })
    }

    private fun observeHourlyForecast() {
        viewModel.hourlyForecastLiveData.observe(viewLifecycleOwner, { forecastList ->
            run {
                hourlyAdapter = HourlyListAdapter(forecastList)
                hourlyList.adapter = hourlyAdapter
                hourlyAdapter.notifyItemInserted(forecastList.size - 1)
            }

        })
    }

    private fun observeDailyForecast() {
        viewModel.dailyForecastLiveData.observe(viewLifecycleOwner, { forecastList ->
            run {
                dailyAdapter = DailyListAdapter(forecastList)
                dailyList.adapter = dailyAdapter
                dailyAdapter.notifyItemInserted(forecastList.size - 1)
            }

        })
    }

    private fun observeCity() {
        viewModel.forecastLiveData.observe(viewLifecycleOwner, { cityData ->
            run {
                city.text = cityData.city
            }

        })
    }
}