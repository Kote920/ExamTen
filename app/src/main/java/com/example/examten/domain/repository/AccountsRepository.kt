package com.example.examten.domain.repository

import com.example.examten.data.common.Resource
import com.example.examten.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun getAccounts(): Flow<Resource<List<Account>>>
    suspend fun getAccount(): Flow<Resource<Account>>
}