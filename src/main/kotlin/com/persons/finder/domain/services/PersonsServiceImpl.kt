package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto
import com.persons.finder.repository.PersonsRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(private val personsRepository: PersonsRepository) : PersonsService {
    // Retrieves a person by their ID from the repository.
    override fun getById(id: Long): Person {
        return personsRepository.findById(id).get()
    }

    // Retrieves a list of persons by their IDs from the repository.
    override fun getByIds(id: List<Long>): ResponseDto<Person> {
        val people = personsRepository.findAllById(id)
        return ResponseDto(
            count = people.size,
            rows = people
        )
    }

    // Saves a person to the repository and returns the person's ID. Will update if the person already exists.
    override fun save(person: Person): Long {
        personsRepository.save(person)
        return person.id
    }

}