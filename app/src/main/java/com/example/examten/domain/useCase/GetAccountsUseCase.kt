package com.example.examten.domain.useCase

import com.example.examten.domain.repository.AccountsRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(private val repository: AccountsRepository) {


    suspend operator fun invoke() = repository.getAccounts()
}