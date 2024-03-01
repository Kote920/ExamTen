package com.example.examten.domain.useCase

import com.example.examten.data.common.Resource
import com.example.examten.domain.model.Account
import com.example.examten.domain.repository.AccountsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ValidateNumberUseCase @Inject constructor(private val repository: AccountsRepository) {


//    suspend operator fun invoke() = repository.geta()

    suspend fun validate(accountNumber: String): Flow<Resource<Account>> =

        flow {
            repository.getAccount().collect {
                when (it) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Success -> {
                        val resultAccNum = it.responseData!!.accountNumber
                        if (accountNumber == resultAccNum){
                            emit(Resource.Success(it.responseData))
                        }else{
                            emit(Resource.Success(null))
                        }
                    }
                    is Resource.Failed -> emit(Resource.Failed(it.message))
                }
            }
        }


}