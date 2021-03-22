package com.reactivemobile.rainfall.domain.repository

import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.model.StationDetails
import kotlinx.coroutines.flow.Flow

interface RainfallRepository {
    suspend fun fetchStationList(): Boolean

    suspend fun getStationDetails(stationId: String): StationDetails?

    fun getStationList(): Flow<List<Station>>
}