package com.persons.finder.seeder

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.random.Random

@Configuration
class DataSeeder(@Value("\${seed.count}") private var seedCount: Int ) {

    @Bean
    fun seedDatabase(
        personsService: PersonsService,
        locationsService: LocationsService
        ) = CommandLineRunner {


        println("Seeding initial data...")
        for (i in 1..seedCount) {
            val person = Person(i.toLong(), "Person $i")

            personsService.save(person)

            val location = Location(
                referenceId = i.toLong(),
                latitude = Random.nextDouble(-90.0,90.0),
                longitude = Random.nextDouble(-180.0,180.0)
            )
            locationsService.addLocation(location)
        }
        println("Seeding completed with $seedCount persons and locations.")


    }

}