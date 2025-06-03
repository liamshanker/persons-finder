package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.repository.LocationsRepository
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl(private val locationsRepository: LocationsRepository) : LocationsService {

    override fun addLocation(location: Location) {
        locationsRepository.save(location)
    }

    override fun removeLocation(locationReferenceId: Long) {
        TODO("Not yet implemented")
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<Location> {
        TODO("Not yet implemented")
    }

}