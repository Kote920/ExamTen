package com.example.examten.data.repository

import android.util.Log
import com.example.examten.data.common.Resource
import com.example.examten.data.remote.mapper.toDomain
import com.example.examten.data.remote.service.AccountsService
import com.example.examten.domain.model.Account
import com.example.examten.domain.repository.AccountsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountsRepositoryImpl @Inject constructor(private val service: AccountsService) :
    AccountsRepository {
    override suspend fun getAccounts(): Flow<Resource<List<Account>>> =
        withContext(Dispatchers.IO) {

            flow {
                try {
                    emit(Resource.Loading())
                    val response = service.getAccounts()
                    if (response.isSuccessful) {
                        val accounts = response.body()!!
                        emit(Resource.Success(accounts.map { it.toDomain() }))
                        Log.d("SuccessResultList", accounts.toString())
                    } else {

                        emit(Resource.Failed("Failed!! with no Exception"))

                    }

                } catch (e: Exception) {
                    emit(Resource.Failed("Failed!"))
                    Log.d("errorRepository", e.toString())
                }


            }

        }

    override suspend fun getAccount(): Flow<Resource<Account>> =
        withContext(Dispatchers.IO) {

            flow {
                try {
                    emit(Resource.Loading())
                    val response = service.getAccount()
                    if (response.isSuccessful) {
                        val accounts = response.body()!!
                        emit(Resource.Success(accounts.toDomain()))
                        Log.d("SuccessResultList", accounts.toString())
                    } else {

                        emit(Resource.Failed("Failed!! with no Exception"))

                    }

                } catch (e: Exception) {
                    emit(Resource.Failed("Failed!"))
                    Log.d("errorRepository", e.toString())
                }


            }

        }


}