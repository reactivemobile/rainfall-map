package com.reactivemobile.rainfall.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import com.reactivemobile.rainfall.presentation.toHumanReadableDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StationDetailsViewModel(private val repository: RainfallRepository, private val stationId: String) : ViewModel() {

    private val _stationDetails = MutableLiveData<StationDetailsEvent>()

    val stationDetails: LiveData<StationDetailsEvent> = _stationDetails

    private val compositeDisposable = CompositeDisposable()

    fun fetchStationDetailsRx() =
        compositeDisposable.add(
            repository.getStationDetailsRx(stationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ details ->
                    _stationDetails.postValue(with(details)
                    {
                        StationDetailsEvent.StationDetailsSuccess(depth, unit, date?.toHumanReadableDate())
                    })
                }, {
                    _stationDetails.postValue(StationDetailsEvent.StationDetailsFailure)
                })
        )

    fun viewDetached() = compositeDisposable.dispose()

    sealed class StationDetailsEvent {
        class StationDetailsSuccess(val depth: Double?, val unit: String?, val date: String?) : StationDetailsEvent()
        object StationDetailsFailure : StationDetailsEvent()
    }
}