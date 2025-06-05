package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto
import com.persons.finder.repository.PersonsRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class PersonsServiceTest {

    @InjectMocks
    lateinit var personsService: PersonsServiceImpl

    @Mock
    lateinit var personsRepository: PersonsRepository

    @Test
    fun `save should call repository Save and return id of saved person`() {
        // Given
        val person = Person(id = 1L, name = "John Doe")

        whenever(personsRepository.save(person))
            .thenReturn(person)

        val savedId = personsService.save(person)

        assertEquals(1L, savedId)
        verify(personsRepository).save(person)
    }


    @Test
    fun `getById should return person when found`() {
        val person = Person(id = 1L, name = "Alice")
        whenever(personsRepository.findById(1L))
            .thenReturn(Optional.of(person))

        val result = personsService.getById(1L)

        assertEquals(person, result)
        verify(personsRepository).findById(1L)
    }


    @Test
    fun `getById should throw NoSuchElementException when person not found`() {
        whenever(personsRepository.findById(2L))
            .thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            personsService.getById(2L)
        }
    }


    @Test
    fun `getByIds should return response with list of persons`() {
        val people = listOf(
            Person(id = 1L, name = "Alice"),
            Person(id = 2L, name = "Bob")
        )

        whenever(personsRepository.findAllById(listOf(1L, 2L))).thenReturn(people)

        val response: ResponseDto<Person> = personsService.getByIds(listOf(1L, 2L))

        assertEquals(2, response.count)
        assertEquals(people, response.rows)
        verify(personsRepository).findAllById(listOf(1L, 2L))
    }


}