package com.example.examten.data.remote.model

import com.squareup.moshi.Json

data class CurrencyDto(
    @Json(name = "course") val rate: Double
)