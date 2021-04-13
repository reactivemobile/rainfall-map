package com.reactivemobile.rainfall.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station_list_table")
data class DbStation(
    @PrimaryKey val id: String,
    val latitude: Double,
    val longitude: Double
)
