package com.reactivemobile.rainfall.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reactivemobile.rainfall.domain.repository.RainfallRepository

class StationDetailsViewModelFactory(private val repository: RainfallRepository) : ViewModelProvider.Factory {

    private lateinit var stationId: String

    fun bind(stationDetailsFragment: StationDetailsFragment) {
        stationId = stationDetailsFragment.requireArguments().getString(EXTRA_STATION_ID) ?: throw RuntimeException("Error EXTRA_STATION_ID is missing")
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StationDetailsViewModel(repository, stationId) as T
    }
}