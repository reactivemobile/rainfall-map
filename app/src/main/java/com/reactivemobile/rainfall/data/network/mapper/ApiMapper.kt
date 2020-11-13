package com.reactivemobile.rainfall.data.network.mapper

import android.icu.text.SimpleDateFormat
import com.reactivemobile.rainfall.data.network.model.ItemDTO
import com.reactivemobile.rainfall.data.network.model.ItemDetailsDTO
import com.reactivemobile.rainfall.domain.model.Station
import java.util.Locale

class ApiMapper {
    fun mapStationDtoToDomainObject(itemDTO: ItemDTO): Station =
        with(itemDTO)
        {
            Station(
                id = stationReference,
                name = label,
                latitude = lat,
                longitude = long
            )
        }

    var simpleDate: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK)

    fun mapStationDetailsDtoToDomainObject(station: Station, itemDetailsDTO: ItemDetailsDTO?): Station =
        itemDetailsDTO?.let {
            return@let station.copy(
                latestReading = it.latestReading.value,
                unit = it.unitName,
                dateTime = simpleDate.format(it.latestReading.dateTime)
            )
        } ?: station
}