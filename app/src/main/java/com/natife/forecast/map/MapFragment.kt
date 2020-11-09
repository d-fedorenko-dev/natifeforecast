package com.natife.forecast.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.natife.forecast.R
import kotlinx.android.synthetic.main.map_fragment.*

/**
 * A fragment representing a list of Items.
 */
class MapFragment : Fragment(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var mMap: GoogleMap? = null
    private lateinit var mLastLocation: Location
    private var mCurrLocationMarker: Marker? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var action = MapFragmentDirections.actionMapFragmentToForecastFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        search.setOnClickListener {
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true


        }

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                val latLng = LatLng(
                    location!!.latitude,
                    location.longitude
                )
                val cameraPosition: CameraPosition = CameraPosition.Builder()
                    .target(latLng) // Sets the center of the map to location user
                    .zoom(6F) // Sets the zoom
                    .build() // Creates a CameraPosition from the builder

                mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                placeMarker(latLng)
            }

        mMap!!.setOnMapLongClickListener {
            placeMarker(it)

        }

    }

    private fun placeMarker(it: LatLng) {
        mMap!!.clear()
        mMap!!.addMarker(MarkerOptions().position(it))

        action =
            MapFragmentDirections.actionMapFragmentToForecastFragment(
                it.latitude.toFloat(),
                it.longitude.toFloat()
            )
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this.requireActivity())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }


    override fun onLocationChanged(location: Location?) {
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        }
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
    }
}