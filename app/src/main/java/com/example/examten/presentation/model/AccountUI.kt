package com.example.examten.presentation.model

data class AccountUI(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valuteType: String,
    val cardType: String,
    val balance: Int?,
    val cardLogo: String?
)