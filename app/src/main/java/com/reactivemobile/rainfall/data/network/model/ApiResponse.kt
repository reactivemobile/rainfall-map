package com.reactivemobile.rainfall.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class StationListDTO(@SerializedName("items") val items: List<ItemDTO>)

data class StationDetailsDTO(@SerializedName("items") val items: List<ItemDetailsDTO>)

data class ItemDTO(
    @SerializedName("stationReference") val stationReference: String,
    @SerializedName("label") val label: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("long") val long: Double,
)

data class ItemDetailsDTO(
    @SerializedName("stationReference") val stationReference: String,
    @SerializedName("unitName") val unitName: String,
    @SerializedName("latestReading") val latestReading: LatestReadingDTO
)

data class LatestReadingDTO(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: Double,
    @SerializedName("dateTime") val dateTime: Date
)