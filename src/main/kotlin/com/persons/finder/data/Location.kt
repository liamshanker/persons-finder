package com.persons.finder.data

import javax.persistence.*

// Represents a location with latitude and longitude assigned to a specific ID.
// @Entity annotation is used to map this class to a database table. @Id annotation is used to specify the primary key of the entity.
@Entity
data class Location(

    @Id
    @Column(name = "referenceId", unique = true, nullable = false)
    var referenceId: Long = 0,

    @Column(name = "latitude", nullable = false)
    var latitude: Double = 0.0,

    @Column(name = "longitude", nullable = false)
    var longitude: Double = 0.0,

)
