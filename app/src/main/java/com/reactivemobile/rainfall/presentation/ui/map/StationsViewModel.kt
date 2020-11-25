package com.reactivemobile.rainfall.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class StationsViewModel(private val rainfallRepository: RainfallRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _stationsLoading = MutableLiveData<Boolean>()

    val stationsLoading = _stationsLoading

    private val _stationsListRx = MutableLiveData<StationListState>()

    val stationsListRx: LiveData<StationListState> = _stationsListRx

    fun fetchStationListRx() =
        compositeDisposable.add(rainfallRepository.fetchStationListRx()
            .onErrorReturn { false }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .zipWith(rainfallRepository.getStationListRx(), BiFunction { t1, t2 -> mapDataToState(t2, t1) })
            .doOnSubscribe { showLoading() }
            .doOnComplete { hideLoading() }
            .subscribe { _stationsListRx.postValue(it) }
        )

    private fun showLoading() = _stationsLoading.postValue(true)

    private fun hideLoading() = _stationsLoading.postValue(false)

    fun viewDetached() = compositeDisposable.dispose()

    private fun mapDataToState(cachedList: List<Station>, successfulResponse: Boolean) = when {
        successfulResponse -> StationListState.StationListSuccess(false, cachedList)
        cachedList.isNotEmpty() -> StationListState.StationListSuccess(true, cachedList)
        else -> StationListState.StationListFailure
    }

    sealed class StationListState() {
        class StationListSuccess(val fromCache: Boolean, val stationList: List<Station>) : StationListState()
        object StationListFailure : StationListState()
    }
}