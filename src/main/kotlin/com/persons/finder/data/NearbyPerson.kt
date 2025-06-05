package com.persons.finder.data

// Util data class to help with GET /persons/nearby endpoint.
data class NearbyPerson(
    val id: Long,
    val distance: Double,
)
