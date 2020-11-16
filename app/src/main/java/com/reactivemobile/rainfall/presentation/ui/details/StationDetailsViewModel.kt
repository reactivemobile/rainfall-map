package com.reactivemobile.rainfall.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import com.reactivemobile.rainfall.presentation.toHumanReadableDate
import kotlinx.coroutines.launch

class StationDetailsViewModel(private val repository: RainfallRepository, private val stationId: String) : ViewModel() {

    private val _stationDetails = MutableLiveData<StationDetailsEvent>()

    val stationDetails: LiveData<StationDetailsEvent> = _stationDetails

    fun fetchStationDetails() {
        viewModelScope.launch {

            val stationDetails = repository.getStationDetails(stationId)

            val outcome = if (stationDetails != null) {
                StationDetailsEvent.StationDetailsSuccess(stationDetails.depth, stationDetails.unit, stationDetails.date?.toHumanReadableDate())
            } else {
                StationDetailsEvent.StationDetailsFailure
            }

            _stationDetails.postValue(outcome)
        }
    }

    sealed class StationDetailsEvent {
        class StationDetailsSuccess(val depth: Double?, val unit: String?, val date: String?) : StationDetailsEvent()
        object StationDetailsFailure : StationDetailsEvent()
    }
}