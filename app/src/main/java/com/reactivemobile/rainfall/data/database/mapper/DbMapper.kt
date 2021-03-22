package com.reactivemobile.rainfall.data.database.mapper

import com.reactivemobile.rainfall.data.database.entities.DbStation
import com.reactivemobile.rainfall.data.network.model.ItemDTO
import com.reactivemobile.rainfall.domain.model.Station

interface DbMapper {
    fun mapStationDaoToDbObject(stations: List<ItemDTO>): List<DbStation>

    fun mapDbStationToDomainObject(dbStationDetails: List<DbStation>): List<Station>
}