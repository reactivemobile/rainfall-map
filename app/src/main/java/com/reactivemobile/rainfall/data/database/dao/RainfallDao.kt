package com.reactivemobile.rainfall.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reactivemobile.rainfall.data.database.entities.DbStation
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RainfallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStationsRx(stations: List<DbStation>): Completable

    @Query("SELECT * FROM station_list_table")
    fun getStationsRx(): Observable<List<DbStation>>
}
