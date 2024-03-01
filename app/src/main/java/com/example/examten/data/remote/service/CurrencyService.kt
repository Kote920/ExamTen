package com.example.examten.data.remote.service

import com.example.examten.data.remote.model.AccountDto
import com.example.examten.data.remote.model.CurrencyDto
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {

    @GET("d9eab148-a083-4625-9f9a-9ada0d409ba3?from_account=USD&to_account=EUR")
    suspend fun getCurrency(): Response<CurrencyDto>
}