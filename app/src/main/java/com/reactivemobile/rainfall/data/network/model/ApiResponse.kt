package com.reactivemobile.rainfall.data.network.model

import com.google.gson.annotations.SerializedName

data class StationDTO(
    @SerializedName("stationReference") val stationReference: String,
    @SerializedName("label") val label: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("long") val long: Double
)

data class StationListDTO(@SerializedName("items") val items: List<StationDTO>)