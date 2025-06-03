package com.persons.finder.repository

import com.persons.finder.data.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    fun findByNameIgnoreCase(name: String): List<Person>
}