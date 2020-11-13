package com.reactivemobile.rainfall.data.network.mapper

import com.reactivemobile.rainfall.data.network.model.ItemDTO
import com.reactivemobile.rainfall.data.network.model.ItemDetailsDTO
import com.reactivemobile.rainfall.domain.model.Station
import com.reactivemobile.rainfall.domain.model.StationDetails

class ApiMapper {
    fun mapStationDtoToDomainObject(itemDTO: ItemDTO): Station =
        with(itemDTO)
        {
            Station(
                id = stationReference,
                latitude = lat,
                longitude = long
            )
        }

    fun mapStationDetailsDtoToDomainObject(itemDetailsDTO: ItemDetailsDTO): StationDetails =
        with(itemDetailsDTO)
        {
            StationDetails(id = stationReference, depth = latestReading?.value, unit = unitName, date = latestReading?.dateTime)
        }
}