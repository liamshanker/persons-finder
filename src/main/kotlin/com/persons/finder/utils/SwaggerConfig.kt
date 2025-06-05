package com.persons.finder.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableOpenApi
class SwaggerConfig {
    @Bean
    fun apiDocumentation(): Docket = Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.persons.finder.presentation"))
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(ApiInfo(
            "Persons Finder API",
            "API for managing persons and their locations",
            "1.0",
            null,
            "Liam Shanker",
            null,
            null
        ))
}