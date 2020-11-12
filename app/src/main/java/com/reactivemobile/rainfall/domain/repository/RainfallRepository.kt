package com.reactivemobile.rainfall.domain.repository

import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.domain.model.Station
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RainfallRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val rainfallClient: RainfallClient,
    private val apiMapper: ApiMapper
) {
    suspend fun getStations(): List<Station> =
        try {
            withContext(ioDispatcher) {
                rainfallClient.getStationList().items
                    .filter { it.lat != 0.0 && it.long != 0.0 }
                    .map(apiMapper::mapStationDtoToDomainObject)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
}