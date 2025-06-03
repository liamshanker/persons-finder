package com.persons.finder.data

import javax.persistence.*

@Entity
data class Location(

    @Id
    @Column(name = "referenceId", unique = true, nullable = false)
    var referenceId: Long = 0,

    @Column(name = "latitude", nullable = false)
    var latitude: Double = 0.0,

    @Column(name = "longitude", nullable = false)
    var longitude: Double = 0.0,

    //Foreign Key to Person
    @OneToOne
    @MapsId
    @JoinColumn(name = "referenceId", referencedColumnName = "id")
    var person: Person? = null
)
