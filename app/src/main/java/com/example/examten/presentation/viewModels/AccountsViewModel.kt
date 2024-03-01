package com.example.examten.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examten.data.common.Resource
import com.example.examten.domain.useCase.GetAccountsUseCase
import com.example.examten.presentation.mapper.toPresentation
import com.example.examten.presentation.model.AccountUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(private val getAccountsUseCase: GetAccountsUseCase) :
    ViewModel() {

    private val _accountsFlow = MutableSharedFlow<Resource<List<AccountUI>>>()
    val accountsFlow: SharedFlow<Resource<List<AccountUI>>> = _accountsFlow.asSharedFlow()


    fun getAccounts() {
        viewModelScope.launch {
            getAccountsUseCase.invoke().collect() {
                when (it) {
                    is Resource.Loading -> _accountsFlow.emit(Resource.Loading())
                    is Resource.Success -> {
                        _accountsFlow.emit(Resource.Success(it.responseData!!.map {account->
                            account.toPresentation()
                        }))
                    }

                    is Resource.Failed -> _accountsFlow.emit(Resource.Failed(it.message))
                }
            }
        }
    }
}