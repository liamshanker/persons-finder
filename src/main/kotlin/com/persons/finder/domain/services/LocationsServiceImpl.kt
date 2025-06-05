package com.persons.finder.domain.services

import com.persons.finder.data.BoundingBox
import com.persons.finder.data.Location
import com.persons.finder.repository.LocationsRepository
import org.springframework.stereotype.Service
import com.persons.finder.data.NearbyPerson
import com.persons.finder.data.ResponseDto
import kotlin.math.cos

@Service
class LocationsServiceImpl(private val locationsRepository: LocationsRepository) : LocationsService {

    // Adds a location to the DB, will update if the location already exists.
    override fun addLocation(location: Location) {
        locationsRepository.save(location)
    }

    // Removes a location from DB by its reference ID.
    override fun removeLocation(locationReferenceId: Long) {
        locationsRepository.deleteById(locationReferenceId)
    }

    /**
     * Given a point defined by latitude and longitude, and a radius in kilometers, calculate a bounding box around the point
     * Then query the database for all locations within that bounding box that are within the specified radius.
      */
    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): ResponseDto<NearbyPerson> {
        if (radiusInKm <= 0.0) {
            throw IllegalArgumentException("Radius must be greater than zero")
        }
        val boundingBox = calculateBoundingBox(latitude, longitude, radiusInKm)

        val nearbyPeople = locationsRepository.findWithinRadius(
            latitude = latitude,
            longitude = longitude,
            radiusInKm = radiusInKm,
            minLat = boundingBox.minLat,
            maxLat = boundingBox.maxLat,
            minLon = boundingBox.minLon,
            maxLon = boundingBox.maxLon
        ).map { row ->
            NearbyPerson(
                id = (row[0] as Number).toLong(),
                distance = (row[1] as Number).toDouble()
            )
        }
        return ResponseDto(
            count = nearbyPeople.size,
            rows = nearbyPeople,
        )
    }

    /**
     * Calculates the bounding box for a given latitude, longitude, and radius in kilometers.
     * This box can be imagined as a square(ish) area around the point which encompasses the radius.
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