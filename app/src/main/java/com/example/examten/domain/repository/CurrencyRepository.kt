package com.example.examten.domain.repository

import com.example.examten.data.common.Resource
import com.example.examten.domain.model.Account
import com.example.examten.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun getCurrency(): Flow<Resource<Currency>>

}