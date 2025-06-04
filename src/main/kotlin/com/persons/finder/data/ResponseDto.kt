package com.persons.finder.data

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseDto<T>(

    @JsonProperty("count")
    var count: Int,

    @JsonProperty("rows")
    var rows: List<T>

)
