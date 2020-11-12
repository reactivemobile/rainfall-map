package com.reactivemobile.rainfall.data.network.mapper

import com.reactivemobile.rainfall.data.network.model.StationDTO
import com.reactivemobile.rainfall.domain.model.Station

class ApiMapper {

    fun mapStationDtoToDomainObject(stationDTO: StationDTO): Station =
        with(stationDTO)
        {
            Station(id = stationReference, name = label, latitude = lat, longitude = long)
        }
}