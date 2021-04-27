package com.reactivemobile.rainfall.domain.model

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("id")
    val id: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)