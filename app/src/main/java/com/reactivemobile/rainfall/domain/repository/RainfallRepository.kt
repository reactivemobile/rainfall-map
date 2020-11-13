package com.reactivemobile.rainfall.domain.repository

import com.reactivemobile.rainfall.data.database.dao.RainfallDao
import com.reactivemobile.rainfall.data.database.mapper.DbMapper
import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.data.network.model.StationDetailsDTO
import com.reactivemobile.rainfall.domain.model.StationDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RainfallRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val rainfallClient: RainfallClient,
    private val apiMapper: ApiMapper,
    private val rainfallDao: RainfallDao,
    private val dbMapper: DbMapper
) {
    suspend fun fetchStationList() = withContext(ioDispatcher) {
        val stations =
            try {
                rainfallClient.getStationList().items
                    .filter { it.lat != 0.0 && it.long != 0.0 } // Some stations are not reporting location correctly
            } catch (e: Exception) {
                null
            }

        if (stations != null) {
            rainfallDao.insertStations(dbMapper.mapStationDaoToDbObject(stations))
        }
    }

    suspend fun getStationDetails(stationId: String): StationDetails? = withContext(ioDispatcher) {
        val response: StationDetailsDTO = rainfallClient.getStationDetails(stationId)

        response.items.firstOrNull()?.let {
            apiMapper.mapStationDetailsDtoToDomainObject(it)
        }
    }

    fun getStationList() =
        rainfallDao
            .getStations()
            .map { dbMapper.mapDbStationToDomainObject(it) }
}