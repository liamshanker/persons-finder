package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.data.NearbyPerson
import com.persons.finder.data.ResponseDto

interface LocationsService {
    fun addLocation(location: Location)
    fun removeLocation(locationReferenceId: Long)
    fun findAround(latitude: Double, longitude: Double, radiusInKm: Double) : ResponseDto<NearbyPerson>
}