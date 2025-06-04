package com.persons.finder.data

// Util data class to help with PUT /persons/{id}/location endpoint.
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
