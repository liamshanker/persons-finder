package com.persons.finder.presentation

import com.persons.finder.data.Coordinates
import com.persons.finder.data.Person
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

    /*
        TODO GET API to retrieve people around query location with a radius in KM, Use query param for radius.
        TODO API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
        // API would be called using John's id and a radius 10km
     */

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