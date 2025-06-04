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
        ): CommandLineRunner = CommandLineRunner {
        if (seedCount <= 0) {
            println("No seeding required, seed count is set to $seedCount. If seeding is required, please set a positive value for 'seed.count' in application properties.")
            return@CommandLineRunner
        }
        println("Seeding initial data...")
        for (i in 1..seedCount) {
            if (personsService.getById(i.toLong()) != null) { continue }
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