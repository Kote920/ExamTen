package com.example.examten.data.remote.mapper

import com.example.examten.data.remote.model.CurrencyDto
import com.example.examten.domain.model.Currency

fun CurrencyDto.toDomain()= Currency(rate = rate)