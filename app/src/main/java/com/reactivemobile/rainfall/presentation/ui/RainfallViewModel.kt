package com.reactivemobile.rainfall.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import kotlinx.coroutines.launch

class RainfallViewModel(private val rainfallRepository: RainfallRepository) : ViewModel() {

    val stations: LiveData<List<Station>> = rainfallRepository.getStationList().asLiveData()

    fun fetchStationList() = viewModelScope.launch { rainfallRepository.fetchStationList() }
}