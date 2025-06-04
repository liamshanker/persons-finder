package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.repository.PersonsRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(private val personsRepository: PersonsRepository) : PersonsService {

    override fun getByIds(id: List<Long>): List<Person> {
        return personsRepository.findAllById(id)
    }

    override fun save(person: Person): Long {
        personsRepository.save(person)
        return person.id
    }

}