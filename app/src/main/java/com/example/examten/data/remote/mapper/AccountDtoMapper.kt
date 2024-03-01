package com.example.examten.data.remote.mapper

import com.example.examten.data.remote.model.AccountDto
import com.example.examten.domain.model.Account

fun AccountDto.toDomain() = Account(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
    cardLogo = cardLogo
)