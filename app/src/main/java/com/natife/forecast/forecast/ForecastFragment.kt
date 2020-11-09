package com.natife.forecast.forecast

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.natife.forecast.R
import com.natife.forecast.forecast.adapters.DailyListAdapter
import com.natife.forecast.forecast.adapters.HourlyListAdapter
import kotlinx.android.synthetic.main.forecast_fragment.*

class ForecastFragment : Fragment() {
    private val viewModel: ForecastViewModel by activityViewModels()

    private lateinit var hourlyLinearLayoutManager: LinearLayoutManager
    private lateinit var dailyLinearLayoutManager: LinearLayoutManager

    private lateinit var hourlyAdapter: HourlyListAdapter
    private lateinit var dailyAdapter: DailyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )

        currentLocation.setOnClickListener {
            findNavController().navigate(R.id.action_forecastFragment_to_locationFragment)
        }
        cityLine.setOnClickListener {
            findNavController().navigate(R.id.action_ForecastFragment_to_mapFragment)
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

        observe()

        val lat = ForecastFragmentArgs.fromBundle(requireArguments()).latitude
        val lon = ForecastFragmentArgs.fromBundle(requireArguments()).longitude
        if (lon.toDouble() != 0.0 && lon.toDouble() != 0.0) {
            viewModel.getUpdateCity(lon.toDouble(), lat.toDouble())
        } else
            if (ContextCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(this.requireActivity())
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            viewModel.setCoords(location.latitude, location.longitude)
                        }
                        viewModel.getCity()
                    }.addOnFailureListener() {
                        viewModel.getCity()
                    }.addOnCanceledListener {
                        viewModel.getCity()
                    }

            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this.requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(this.requireActivity())
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                viewModel.setCoords(location?.latitude!!, location.longitude!!)
                                viewModel.getCity()
                            }.addOnCanceledListener {
                                viewModel.getCity()
                            }

                    }
                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun observe() {
        observeCity()

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
                wetherIcon.setImageResource(forecast.weatherIcon)
                windIcon.setImageResource(forecast.windIcon)
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