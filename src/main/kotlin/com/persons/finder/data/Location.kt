package com.persons.finder.data

import javax.persistence.*

@Entity
data class Location(

    @Id
    @Column(name = "referenceId", unique = true, nullable = false)
    var referenceId: Long,

    @Column(name = "latitude", nullable = false)
    var latitude: Double,

    @Column(name = "longitude", nullable = false)
    var longitude: Double,

    @OneToOne
    @MapsId
    @JoinColumn(name = "referenceId")
    var person: Person? = null
)
