package com.persons.finder.seeder

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.repository.LocationsRepository
import com.persons.finder.repository.PersonsRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.math.round
import kotlin.random.Random

/**
 * This configuration class seeds the database with initial data for testing purposes.
 * Command line runner is used to execute the seeding logic after the application context is loaded but before the application starts serving requests
 */
@Configuration
class DataSeeder(@Value("\${seed.count}") private var seedCount: Int ) {

    @Bean
    fun seedDatabase(
        personsRepository: PersonsRepository,
        locationsRepository: LocationsRepository
    ): CommandLineRunner = CommandLineRunner {
        if (seedCount <= 0) {
            println("Seed count is set to $seedCount, database has been cleared. If seeding is required, please set a positive value for 'seed.count' in application properties.")
            personsRepository.deleteAll()
            locationsRepository.deleteAll()
            return@CommandLineRunner
        }
        val batchSize = round((seedCount / 50).toDouble()).toInt()
        println("Seeding initial data...")
        for (i in 1..seedCount step batchSize) {
            println("Seeding persons $i to ${i+batchSize}")

            val persons = (i until (i + batchSize)).map {
                Person(it.toLong(), "Person $it")
            }

            personsRepository.saveAll(persons)

            println("Seeding locations for persons $i to ${i+batchSize}")

            val locations = persons.map {
                Location(
                    referenceId = it.id,
                    latitude = Random.nextDouble(-90.0, 90.0),
                    longitude = Random.nextDouble(-180.0, 180.0)
                )
            }
            locationsRepository.saveAll(locations)
        }
        println("Seeding completed with $seedCount persons and locations.")


    }

}