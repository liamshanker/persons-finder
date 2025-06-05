package com.persons.finder.data

import com.fasterxml.jackson.annotation.JsonProperty

// Represents a generic response DTO that can be used for list responses
data class ResponseDto<T>(

    @JsonProperty("count")
    var count: Int,

    @JsonProperty("rows")
    var rows: List<T>

)
