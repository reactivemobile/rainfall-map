package com.reactivemobile.rainfall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reactivemobile.rainfall.data.database.dao.RainfallDao
import com.reactivemobile.rainfall.data.database.entities.DbStation

private const val DB_NAME = "rainfall_database"

@Database(entities = [(DbStation::class)], version = 1)
abstract class RainfallDatabase : RoomDatabase() {

    abstract fun rainfallDao(): RainfallDao

    companion object {
        fun create(context: Context): RainfallDatabase {

            return Room.databaseBuilder(
                context,
                RainfallDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
