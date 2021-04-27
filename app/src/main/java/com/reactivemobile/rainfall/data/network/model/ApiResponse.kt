package com.reactivemobile.rainfall.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class StationListDTO(@SerializedName("items") val items: List<ItemDTO>)

data class ItemDTO(
    @SerializedName("stationReference") val stationReference: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("long") val long: Double,
)

data class StationDetailsDTO(@SerializedName("items") val items: List<ItemDetailsDTO>)

data class ItemDetailsDTO(
    @SerializedName("stationReference") val stationReference: String,
    @SerializedName("unitName") val unitName: String,
    @SerializedName("latestReading") val latestReading: LatestReadingDTO?,
    @SerializedName("qualifier") val qualifier: String
)

data class LatestReadingDTO(
    @SerializedName("value") val value: Double,
    @SerializedName("dateTime") val dateTime: Date
)