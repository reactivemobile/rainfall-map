package com.reactivemobile.rainfall.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.application.RainfallApplication
import com.reactivemobile.rainfall.domain.model.Station
import kotlinx.android.synthetic.main.fragment_rainfall.*
import javax.inject.Inject

class RainfallFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_rainfall, container, false)

    private lateinit var googleMap: GoogleMap

    @Inject
    lateinit var viewModelFactory: RainfallViewModelFactory

    private val viewModel: RainfallViewModel by activityViewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()

        setupObservers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as RainfallApplication).appComponent.inject(this)
    }

    private fun loadStations() {
        viewModel.fetchStationList()
    }

    private fun setupObservers() {
        viewModel.stations.observe(viewLifecycleOwner, { stationList ->
            updateMap(stationList)
        })
    }

    private fun updateMap(stationList: List<Station>) {
        googleMap.clear()

        val latLngBoundsBuilder = LatLngBounds.builder()

        for (station in stationList) {
            val position = LatLng(station.latitude, station.longitude)
            val marker = MarkerOptions().position(position).title(station.id)

            latLngBoundsBuilder.include(position)
            googleMap.addMarker(marker).tag = station
        }

        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), 0)
        googleMap.animateCamera(cameraUpdate)
    }

    private fun setupMap() {
        map.onCreate(null)

        val callback = OnMapReadyCallback {
            googleMap = it
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.isMapToolbarEnabled = false
            googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(layoutInflater))

            loadStations()

            map.onResume()
        }

        map.getMapAsync(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = RainfallFragment()

        val TAG: String = RainfallFragment::class.java.name
    }

    class CustomInfoWindowAdapter(layoutInflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

        private val customLayout: View = layoutInflater.inflate(R.layout.map_marker_info_contents, null)

        override fun getInfoWindow(p0: Marker?): View? {
            return null
        }

        override fun getInfoContents(marker: Marker): View {
            val station = marker.tag as Station

            return customLayout
        }
    }
}