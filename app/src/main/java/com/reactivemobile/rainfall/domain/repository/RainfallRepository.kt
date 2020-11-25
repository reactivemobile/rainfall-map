package com.reactivemobile.rainfall.domain.repository

import com.reactivemobile.rainfall.data.database.dao.RainfallDao
import com.reactivemobile.rainfall.data.database.mapper.DbMapper
import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.data.network.model.ItemDTO
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.model.StationDetails
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RainfallRepository @Inject constructor(
    private val rainfallClient: RainfallClient,
    private val apiMapper: ApiMapper,
    private val rainfallDao: RainfallDao,
    private val dbMapper: DbMapper
) {
    fun fetchStationListRx(): Single<Boolean> =
        rainfallClient.getStationListRx().map { it.items }
            .flatMapObservable { it -> Observable.fromIterable(it) }
            .filter(this::checkIsNotNullIsland)
            .toList()
            .map { dbMapper.mapStationDaoToDbObject(it) }
            .flatMapCompletable { rainfallDao.insertStationsRx(it) }
            .toSingleDefault(true)

    fun getStationDetailsRx(stationId: String): Single<StationDetails> =
        rainfallClient.getStationDetailsRx(stationId)
            .map {
                apiMapper.mapStationDetailsDtoToDomainObject(it.items.first())
            }

    private fun checkIsNotNullIsland(it: ItemDTO) = it.lat != 0.0 && it.long != 0.0

    fun getStationListRx(): Observable<List<Station>> {
        return rainfallDao.getStationsRx().map { dbMapper.mapDbStationToDomainObject(it) }
    }
}