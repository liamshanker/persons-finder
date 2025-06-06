package com.persons.finder.repository

import com.persons.finder.data.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

// Instantiate Locations Repository with all default CRUD operations
@Repository
interface PersonsRepository: JpaRepository<Person, Long>, JpaSpecificationExecutor<Person>