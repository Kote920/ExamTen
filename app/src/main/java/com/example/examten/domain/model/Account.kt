package com.example.examten.domain.model

import com.squareup.moshi.Json

data class Account(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valuteType: String,
    val cardType: String,
    val balance: Int?,
    val cardLogo: String?,
)