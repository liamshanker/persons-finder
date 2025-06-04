package com.persons.finder.domain.services

import com.persons.finder.data.BoundingBox
import com.persons.finder.data.Location
import com.persons.finder.repository.LocationsRepository
import org.springframework.stereotype.Service
import com.persons.finder.data.NearbyPersons
import kotlin.math.cos

@Service
class LocationsServiceImpl(private val locationsRepository: LocationsRepository) : LocationsService {

    override fun addLocation(location: Location) {
        locationsRepository.save(location)
    }

    override fun removeLocation(locationReferenceId: Long) {
        TODO("Not yet implemented")
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<NearbyPersons> {
        if (radiusInKm <= 0.0) {
            throw IllegalArgumentException("Radius must be greater than zero")
        }
        val boundingBox = calculateBoundingBox(latitude, longitude, radiusInKm)

        return locationsRepository.findWithinRadius(
            latitude = latitude,
            longitude = longitude,
            radiusInKm = radiusInKm,
            minLat = boundingBox.minLat,
            maxLat = boundingBox.maxLat,
            minLon = boundingBox.minLon,
            maxLon = boundingBox.maxLon
        ).map { row ->
            NearbyPersons(
                id = (row[0] as Number).toLong(),
                distance = (row[1] as Number).toDouble()
            )
        }
    }

    /**
     * Calculates the bounding box for a given latitude, longitude, and radius in kilometers.
     * This box can be imagined as a square area around the point which encompasses the radius.
     * This is used to optimize the search for nearby persons by limiting the search area.
     */
    private fun calculateBoundingBox(latitude: Double, longitude: Double, radiusInKm: Double): BoundingBox {
        val earthRadius = 6371.0 // Radius of the Earth in kilometers
        val latDelta = Math.toDegrees(radiusInKm / earthRadius)
        val lonDelta = Math.toDegrees(radiusInKm / (earthRadius * cos(Math.toRadians(latitude))))

        return BoundingBox(
            minLat = latitude - latDelta,
            maxLat = latitude + latDelta,
            minLon = longitude - lonDelta,
            maxLon = longitude + lonDelta
        )
    }

}