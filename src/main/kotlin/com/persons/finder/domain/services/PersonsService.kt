package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto

interface PersonsService {
    fun getById(id: Long): Person
    fun getByIds(id: List<Long>): ResponseDto<Person>
    fun save(person: Person): Long
}