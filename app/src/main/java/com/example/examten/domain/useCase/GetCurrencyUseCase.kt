package com.example.examten.domain.useCase

import com.example.examten.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke() = repository.getCurrency()
}