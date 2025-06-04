package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.repository.PersonsRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


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

        whenever(personsRepository.save(person)).thenReturn(person)

        val savedId = personsService.save(person)

        Assertions.assertEquals(1L, savedId)
        verify(personsRepository).save(person)
    }

}