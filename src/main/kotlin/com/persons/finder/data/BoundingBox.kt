package com.persons.finder.data

// Represents a bounding box of coordinates defined by latitude and longitude coordinates.
data class BoundingBox(
    val minLat: Double,
    val maxLat: Double,
    val minLon: Double,
    val maxLon: Double
)
