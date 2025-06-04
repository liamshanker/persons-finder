package com.persons.finder.data

data class Coordinates(
    val latitude: Double,
    val longitude: Double
) {
    fun toLocation(personId: Long): Location {
        return Location(
            referenceId = personId,
            latitude = latitude,
            longitude = longitude
        )
    }
}
