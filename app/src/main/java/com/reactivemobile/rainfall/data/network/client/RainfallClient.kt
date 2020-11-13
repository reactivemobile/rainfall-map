package com.reactivemobile.rainfall.data.network.client

import com.reactivemobile.rainfall.data.network.model.StationDetailsDTO
import com.reactivemobile.rainfall.data.network.model.StationListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RainfallClient {

    @GET("/flood-monitoring/id/stations?parameter=rainfall")
    suspend fun getStationList(): StationListDTO

    @GET("/flood-monitoring/id/measures?parameter=rainfall")
    suspend fun getStationDetails(@Query("stationReference") stationId: String): StationDetailsDTO
}