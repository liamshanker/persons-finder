package com.persons.finder.presentation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import com.persons.finder.data.Coordinates
import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
// Order matters here as the 1st test will create a person, the 2nd will update their location. 2nd test will fail if person doesn't exist
internal class PersonControllerTest(@Value("\${seed.count}") private var seedCount: Int ) {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private fun url(path: String) = "http://localhost:$port/api/v1/persons$path"

    private val person = Person(id = 1L, name = "John Doe")
    private val coordinates = Coordinates(latitude = 12.34, longitude = 56.78)


    @Test
    @Order(1)
    fun `should create a new person`() {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = HttpEntity(person, headers)
        val response = restTemplate.postForEntity(url(""), request, String::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals("1", response.body)
        assertTrue(response.headers.location.toString().endsWith("/api/v1/persons/1"))
    }

    @Test
    @Order(2)
    fun `should update location for existing person`() {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val request = HttpEntity(coordinates, headers)
        val response = restTemplate.exchange(
            url("/1/location"),
            HttpMethod.PUT,
            request,
            Void::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    @Order(3)
    fun `should return bad request for invalid radius`() {
        val url = url("/nearby?latitude=10.0&longitude=20.0&radiusInKm=-1.0")
        val response = restTemplate.getForEntity(url, String::class.java)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(4)
    fun `should return persons by ids`() {
        val url = url("?id=1")
        val response = restTemplate.getForEntity(url, ResponseDto::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

}
