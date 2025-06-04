package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.repository.PersonsRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(private val personsRepository: PersonsRepository) : PersonsService {

    override fun getById(id: Long): Person {
        return personsRepository.findById(id).get()
    }

    override fun save(person: Person): Long {
        personsRepository.save(person)
        return person.id
    }

}