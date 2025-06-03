package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.repository.PersonsRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(private val personsRepository: PersonsRepository) : PersonsService {

    override fun getById(id: Long): Person {
        TODO("Not yet implemented")
    }

    override fun save(person: Person) {
        personsRepository.save(person)
    }

}