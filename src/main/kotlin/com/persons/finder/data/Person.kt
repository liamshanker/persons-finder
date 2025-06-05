package com.persons.finder.data
import javax.persistence.*

// Represents a person in the system.
// @Entity annotation is used to map this class to a database table. @Id annotation is used to specify the primary key of the entity.
@Entity
data class Person(

    @Id
    @Column(name = "id", unique = true, nullable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = "",
)
