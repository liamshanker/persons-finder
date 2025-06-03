package com.persons.finder.repository

import com.persons.finder.data.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface LocationsRepository: JpaRepository<Location, Long>, JpaSpecificationExecutor<Location>