package com.persons.finder.presentation

import com.persons.finder.data.Coordinates
import com.persons.finder.data.NearbyPerson
import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import com.persons.finder.repository.PersonsRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI

@Api(tags = ["Persons API"])
@RestController
@RequestMapping("api/v1/persons")
class PersonController (
    private val personsService: PersonsService,
    private val locationsService: LocationsService,
    private val personsRepository: PersonsRepository
) {

    // PUT API to create/update a person's location
    // This endpoint expects a person's ID in path and coordinates in the request body in JSON format.
    @ApiOperation("Update a person's location")
    @ApiResponses(
        ApiResponse(code = 200, message = "Location updated successfully"),
        ApiResponse(code = 404, message = "Person not found")
    )
    @PutMapping("/{id}/location")
    @ResponseStatus(HttpStatus.OK)
    fun updateLocation(@PathVariable id: Long, @RequestBody coordinates: Coordinates) {
        return try {
            // Check if the person exists
            personsService.getById(id)
            val location = coordinates.toLocation(id)
            locationsService.addLocation(location)
        } catch (ex: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")
        }

    }

    //POST API to create a person
    // This endpoint expects a JSON object containing id & name in the request body and returns the created person's ID.
    @ApiOperation("Create a new person")
    @ApiResponses(
        ApiResponse(code = 201, message = "Person created successfully"),
        ApiResponse(code = 409, message = "Person already exists")
    )
    @PostMapping("")
    fun createPerson(@RequestBody person: Person): ResponseEntity<Long> {
        if (personsRepository.existsById(person.id)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Person with name ${person.name} already exists")
        }
        val createdId = personsService.save(person)
        val createdLocation = URI.create("/api/v1/persons/$createdId")

        return ResponseEntity.created(createdLocation).body(createdId)
    }


    /* NOTE FOR REVIEWER: ReadMe instructs this endpoint to accept params for longitude, latitude and radius,
        but the instructions in this Controller file said to accept a person's ID & radius as params.
        I have followed the ReadMe as it was more recently updated than the Controller file.
     */
    // GET API to find persons within a radius around a given latitude and longitude
    // This endpoint expects latitude, longitude, and radius (all as Double) as query parameters.
    @ApiOperation("Find a nearby person")
    @ApiResponses(
        ApiResponse(code = 200, message = "Nearby persons retrieved"),
        ApiResponse(code = 400, message = "Invalid radius value")
    )
    @GetMapping("/nearby")
    fun getNearbyPersons(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam radiusInKm: Double
    ): ResponseEntity<ResponseDto<NearbyPerson>> {
        return try {
            ResponseEntity.ok(locationsService.findAround(latitude, longitude, radiusInKm))
        } catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid radius value: $radiusInKm")
        }
    }

    // GET API to retrieve persons by a list of IDs
    // This endpoint expects a list of person IDs (as Long) as a query parameters.
    @ApiOperation("Get persons by IDs")
    @ApiResponses(
        ApiResponse(code = 200, message = "Persons retrieved"),
        ApiResponse(code = 400, message = "Invalid ID list")
    )
    @GetMapping("")
    fun getPersons(@RequestParam id: List<Long>): ResponseEntity<ResponseDto<Person>> {
        return try {
            ResponseEntity.ok(personsService.getByIds(id))
        } catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "List IDs must not be null or contain any null values")
        }
    }

}