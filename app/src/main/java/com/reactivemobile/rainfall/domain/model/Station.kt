package com.reactivemobile.rainfall.domain.model

data class Station(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val latestReading: Double? = null,
    val unit: String? = null,
    val dateTime: String? = null
)