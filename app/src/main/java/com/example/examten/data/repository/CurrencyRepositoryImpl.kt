package com.example.examten.data.repository

import android.util.Log
import com.example.examten.data.common.Resource
import com.example.examten.data.remote.mapper.toDomain
import com.example.examten.data.remote.service.CurrencyService
import com.example.examten.domain.model.Currency
import com.example.examten.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val service: CurrencyService) : CurrencyRepository {
    override suspend fun getCurrency(): Flow<Resource<Currency>> =
        withContext(Dispatchers.IO) {

            flow {
                try {
                    emit(Resource.Loading())
                    val response = service.getCurrency()
                    if (response.isSuccessful) {
                        val currency = response.body()!!
                        emit(Resource.Success(currency.toDomain()))
                        Log.d("SuccessResultList", currency.toString())
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