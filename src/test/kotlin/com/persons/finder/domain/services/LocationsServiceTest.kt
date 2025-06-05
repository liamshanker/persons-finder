package com.persons.finder.domain.services

import com.persons.finder.data.Location
import org.junit.jupiter.api.assertThrows
import com.persons.finder.repository.LocationsRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class LocationsServiceTest {
    @InjectMocks
    lateinit var locationsService: LocationsServiceImpl

    @Mock
    lateinit var locationsRepository: LocationsRepository


    @Test
    fun `addLocation should save location`() {
        val location = Location(1L, 10.0, 20.0)
        locationsService.addLocation(location)
        verify(locationsRepository).save(location)
    }

    @Test
    fun `removeLocation should delete location by id`() {
        val locationId = 1L
        locationsService.removeLocation(locationId)
        verify(locationsRepository).deleteById(locationId)
    }

    @Test
    fun `findAround should return nearby people within bounding box`() {
        val latitude = 10.0
        val longitude = 20.0
        val radius = 5.0

        val mockResult = listOf(
            arrayOf(1L, 1.5),
            arrayOf(2L, 2.0)
        )

        whenever(locationsRepository.findWithinRadius(
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble(),
            anyDouble()
        )).thenReturn(mockResult as List<Array<Any>>?)

        val response = locationsService.findAround(latitude, longitude, radius)

        assertEquals(2, response.count)
        assertEquals(1L, response.rows[0].id)
        assertEquals(1.5, response.rows[0].distance)
        assertEquals(2L, response.rows[1].id)
        assertEquals(2.0, response.rows[1].distance)
    }


    @Test
    fun `findAround should throw exception for non-positive radius`() {
        assertThrows<IllegalArgumentException> {
            locationsService.findAround(10.0, 20.0, 0.0)
        }
    }


}