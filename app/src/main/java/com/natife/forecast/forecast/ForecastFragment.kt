package com.natife.forecast.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.natife.forecast.R
import kotlinx.android.synthetic.main.forecast_fragment.*

class ForecastFragment : Fragment() {
    private val viewModel: ForecastViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_locationFragment)
        }
        observeForecast()
    }

    private fun observeForecast() {
        viewModel.forecastLiveData.observe(viewLifecycleOwner, { forecast ->
            run {
                temp.text = forecast.temp
                humidity.text = forecast.hum
                city.text = forecast.city
                wind.text = forecast.wind
                date.text = forecast.date
                decor.text = ""
            }

        })
    }
}