package com.reactivemobile.rainfall.presentation.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import com.reactivemobile.rainfall.presentation.toHumanReadableDate
import kotlinx.coroutines.launch

class StationDetailsViewModel @ViewModelInject constructor(
    private val repository: RainfallRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val stationId = savedStateHandle.get<String>(EXTRA_STATION_ID)

    private val _stationDetails = MutableLiveData<StationDetailsEvent>()

    val stationDetails: LiveData<StationDetailsEvent> = _stationDetails

    fun fetchStationDetails() {
        viewModelScope.launch {

            val stationDetails = repository.getStationDetails(stationId!!)

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