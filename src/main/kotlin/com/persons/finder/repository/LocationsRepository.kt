package com.persons.finder.repository

import com.persons.finder.data.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

// Instantiate Locations Repository with all default CRUD operations & custom queries
@Repository
interface LocationsRepository : JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    /**
    Query uses the Haversine formula to calculate distances between two points on the Earth (pointA is the queried location, pointB is the location in the database)
    To optimize the query, we first filter locations by latitude and longitude bounding box, then calculate the distance for those locations.
    The bounding box is explained in LocationsServiceImpl.kt
    **/
    @Query(
        """
    SELECT reference_id, distance FROM (
        SELECT reference_id,
               (
                   6371 * acos(
                       cos(radians(:latitude)) * cos(radians(latitude)) *
                       cos(radians(longitude) - radians(:longitude)) +
                       sin(radians(:latitude)) * sin(radians(latitude))
                   )
               ) AS distance
        FROM Location
        WHERE latitude BETWEEN :minLat AND :maxLat
          AND longitude BETWEEN :minLon AND :maxLon
    ) AS distances
    WHERE distance <= :radiusInKm
    ORDER BY distance
    """,
        nativeQuery = true
    )
    fun findWithinRadius(
        @Param("latitude") latitude: Double,
        @Param("longitude") longitude: Double,
        @Param("radiusInKm") radiusInKm: Double,
        @Param("minLat") minLat: Double,
        @Param("maxLat") maxLat: Double,
        @Param("minLon") minLon: Double,
        @Param("maxLon") maxLon: Double
    ): List<Array<Any>>

}

