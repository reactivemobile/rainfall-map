package com.reactivemobile.rainfall.data.network.client

import com.reactivemobile.rainfall.data.network.model.StationDetailsDTO
import com.reactivemobile.rainfall.data.network.model.StationListDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RainfallClient {

    @GET("/flood-monitoring/id/stations?parameter=rainfall")
    suspend fun getStationList(): StationListDTO

    @GET("/flood-monitoring/id/stations?parameter=rainfall")
    fun getStationListRx(): Single<StationListDTO>

    @GET("/flood-monitoring/id/measures?parameter=rainfall")
    suspend fun getStationDetails(@Query("stationReference") stationId: String): StationDetailsDTO

    @GET("/flood-monitoring/id/measures?parameter=rainfall")
    fun getStationDetailsRx(@Query("stationReference") stationId: String): Single<StationDetailsDTO>
}