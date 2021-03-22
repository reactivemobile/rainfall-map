package com.reactivemobile.rainfall.data.network.mapper

import com.reactivemobile.rainfall.data.network.model.ItemDetailsDTO
import com.reactivemobile.rainfall.domain.model.StationDetails

class ApiMapperImpl : ApiMapper {

    override fun mapStationDetailsDtoToDomainObject(itemDetailsDTO: ItemDetailsDTO): StationDetails =
        with(itemDetailsDTO)
        {
            StationDetails(id = stationReference, depth = latestReading?.value, unit = unitName, date = latestReading?.dateTime)
        }
}