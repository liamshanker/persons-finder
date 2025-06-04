package com.persons.finder.data
import javax.persistence.*

@Entity
data class Person(

    @Id
    @Column(name = "id", unique = true, nullable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = "",
)
