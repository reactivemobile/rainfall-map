package com.reactivemobile.rainfall.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class StationsViewModel(private val rainfallRepository: RainfallRepository) : ViewModel() {

    private val responseFlow = MutableStateFlow(true)

    private val _stationsLoading = MutableLiveData<Boolean>()

    val stationsLoading = _stationsLoading

    val stationsList: LiveData<StationListState> =
        rainfallRepository
            .getStationList()
            .combine(responseFlow) { list, successfulFetch -> mapDataToState(list, successfulFetch) }
            .asLiveData()

    private fun mapDataToState(cachedList: List<Station>, successfulResponse: Boolean) = when {
        successfulResponse -> StationListState.StationListSuccess(false, cachedList)
        cachedList.isNotEmpty() -> StationListState.StationListSuccess(true, cachedList)
        else -> StationListState.StationListFailure
    }

    fun fetchStationList() {
        _stationsLoading.postValue(true)
        viewModelScope.launch {
            responseFlow.emit(rainfallRepository.fetchStationList())
        }
        _stationsLoading.postValue(false)
    }

    sealed class StationListState() {
        class StationListSuccess(val fromCache: Boolean, val stationList: List<Station>) : StationListState()
        object StationListFailure : StationListState()
    }
}