package com.reactivemobile.rainfall.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reactivemobile.rainfall.data.database.entities.DbStation
import kotlinx.coroutines.flow.Flow

@Dao
interface RainfallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(stations: List<DbStation>)

    @Query("SELECT * FROM station_list_table")
    fun getStations(): Flow<List<DbStation>>
}
