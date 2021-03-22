package com.reactivemobile.rainfall.data.network.mapper

import com.reactivemobile.rainfall.data.network.model.ItemDetailsDTO
import com.reactivemobile.rainfall.domain.model.StationDetails

interface ApiMapper {
    fun mapStationDetailsDtoToDomainObject(itemDetailsDTO: ItemDetailsDTO): StationDetails
}