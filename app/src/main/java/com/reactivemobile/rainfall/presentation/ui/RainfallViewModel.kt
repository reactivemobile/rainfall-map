package com.reactivemobile.rainfall.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RainfallViewModel @Inject constructor(private val rainfallRepository: RainfallRepository) : ViewModel() {

    val stations: MutableLiveData<List<Station>> = MutableLiveData()

    val stationDetails: MutableLiveData<Station> = MutableLiveData()

    fun fetchStationList() {
        viewModelScope.launch {
            val stationList = rainfallRepository.getStationList()
            stations.postValue(stationList) // TODO map errors
        }
    }

    fun fetchStationDetails(existingStation: Station) {
        viewModelScope.launch {
            val station = rainfallRepository.getStationDetails(station = existingStation)
            stationDetails.postValue(station) // TODO map errors
        }
    }
}