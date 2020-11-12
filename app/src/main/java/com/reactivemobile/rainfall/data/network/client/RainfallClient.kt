package com.reactivemobile.rainfall.data.network.client

import com.reactivemobile.rainfall.data.network.model.StationListDTO
import retrofit2.http.GET

interface RainfallClient {

    @GET("/flood-monitoring/id/stations?parameter=rainfall")
    suspend fun getStationList(): StationListDTO
}