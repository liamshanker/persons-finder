package com.persons.finder.presentation

import com.persons.finder.data.Coordinates
import com.persons.finder.data.NearbyPersons
import com.persons.finder.data.Person
import com.persons.finder.data.ResponseDto
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI

@RestController
@RequestMapping("api/v1/persons")
class PersonController (private val personsService: PersonsService, private val locationsService: LocationsService) {

    // PUT API to create/update a person's location
    @PutMapping("/{id}/location")
    @ResponseStatus(HttpStatus.OK)
    fun updateLocation(@PathVariable id: Long, @RequestBody coordinates: Coordinates) {
        return try {
            // Check if the person exists
            personsService.getById(id)
            val location = coordinates.toLocation(id)
            locationsService.addLocation(location)
//            ResponseEntity("Ok", HttpStatus.OK)
        } catch (ex: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")
        }

    }


    //POST API to create a person
    //TODO: modify method to respond 200 if updated and 201 if created
    @PostMapping("")
    fun createPerson(@RequestBody person: Person): ResponseEntity<Long> {
        val createdId = personsService.save(person)
        val createdLocation = URI.create("/api/v1/persons/$createdId")

        return ResponseEntity.created(createdLocation).body(createdId)
    }


    /* NOTE FOR REVIEWER: ReadMe instructs this endpoint to accept params for longitude, latitude and radius,
        but the instructions in this Controller file said to accept a person's ID & radius as params.
        I have followed the ReadMe as it was more recently updated than the Controller file.
     */
    @GetMapping("/nearby")
    fun getNearbyPersons(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam radiusInKm: Double
    ): ResponseEntity<ResponseDto<NearbyPersons>> {
        return try {
            ResponseEntity.ok(locationsService.findAround(latitude, longitude, radiusInKm))
        } catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid radius value: $radiusInKm")
        }
    }

    /*
        TODO GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     */

    @GetMapping("")
    fun getExample(): String {
        return "Hello Example"
    }

}