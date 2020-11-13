package com.reactivemobile.rainfall.presentation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reactivemobile.rainfall.domain.model.StationDetails
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import kotlinx.coroutines.launch

class StationDetailsViewModel(private val repository: RainfallRepository, private val stationId: String) : ViewModel() {


    init {
        Log.e("XXX", "init $stationId")
    }

    private val _stationDetails = MutableLiveData<StationDetailsEvent>()

    val stationDetails: LiveData<StationDetailsEvent> = _stationDetails

    fun fetchStationDetails() {
        viewModelScope.launch {

            val stationDetails = repository.getStationDetails(stationId)

            val outcome = if (stationDetails != null) {
                StationDetailsEvent.StationDetailsSuccess(stationDetails)
            } else {
                StationDetailsEvent.StationDetailsFailure
            }

            _stationDetails.postValue(outcome)
        }
    }

    sealed class StationDetailsEvent {
        class StationDetailsSuccess(val stationDetails: StationDetails) : StationDetailsEvent()
        object StationDetailsFailure : StationDetailsEvent()
    }
}