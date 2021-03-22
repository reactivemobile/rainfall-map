package com.reactivemobile.rainfall.presentation.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.presentation.gone
import com.reactivemobile.rainfall.presentation.show
import com.reactivemobile.rainfall.presentation.showHide
import com.reactivemobile.rainfall.presentation.ui.details.StationDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stations.*

@AndroidEntryPoint
class StationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_stations, container, false)

    private lateinit var googleMap: GoogleMap

    private val viewModel by viewModels<StationsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupObservers() {
        viewModel.stationsList.observe(viewLifecycleOwner, ::updateMap)

        viewModel.stationsLoading.observe(viewLifecycleOwner, { loadingGroup.showHide(it) })
    }

    private fun updateMap(state: StationsViewModel.StationListState) {
        if (state is StationsViewModel.StationListState.StationListSuccess) {

            errorGroup.gone()

            if (::googleMap.isInitialized) {
                googleMap.clear()

                if (state.fromCache) {
                    Toast.makeText(requireContext(), getString(R.string.loaded_stations_from_cache), Toast.LENGTH_SHORT).show()
                }

                if (state.stationList.isNotEmpty()) {
                    val latLngBoundsBuilder = LatLngBounds.builder()
                    val icon = BitmapDescriptorFactory.fromResource(R.drawable.station_icon)
                    for (station in state.stationList) {
                        val position = LatLng(station.latitude, station.longitude)
                        val marker = MarkerOptions().position(position).title(station.id).icon(icon)

                        latLngBoundsBuilder.include(position)
                        googleMap.addMarker(marker).tag = station
                    }

                    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), 0)
                    googleMap.animateCamera(cameraUpdate)
                }
            }
        } else {
            errorGroup.show()
        }
    }

    private fun setupView() {
        map.onCreate(null)

        val callback = OnMapReadyCallback {
            setupMap(it)

            setupObservers()

            viewModel.fetchStationList()

            retryButton.setOnClickListener { viewModel.fetchStationList() }
        }

        map.getMapAsync(callback)
    }

    private fun setupMap(it: GoogleMap) {
        googleMap = it
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = false

        googleMap.setOnMarkerClickListener { marker ->
            val station = marker.tag as Station
            StationDetailsFragment.newInstance(station.id).show(childFragmentManager, null)
            true
        }

        map.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StationsFragment()

        val TAG: String = StationsFragment::class.java.name
    }
}