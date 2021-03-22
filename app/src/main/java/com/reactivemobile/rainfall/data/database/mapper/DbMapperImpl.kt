package com.reactivemobile.rainfall.data.database.mapper

import com.reactivemobile.rainfall.data.database.entities.DbStation
import com.reactivemobile.rainfall.data.network.model.ItemDTO
import com.reactivemobile.rainfall.domain.model.Station

class DbMapperImpl : DbMapper {

    override fun mapStationDaoToDbObject(stations: List<ItemDTO>): List<DbStation> =
        stations.map {
            with(it) {
                DbStation(
                    id = stationReference,
                    latitude = lat,
                    longitude = long
                )
            }
        }

    override fun mapDbStationToDomainObject(dbStationDetails: List<DbStation>): List<Station> =
        dbStationDetails.map {
            with(it) {
                Station(
                    id = id,
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
}