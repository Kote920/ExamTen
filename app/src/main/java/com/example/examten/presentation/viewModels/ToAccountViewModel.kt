package com.example.examten.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examten.data.common.Resource
import com.example.examten.domain.useCase.GetAccountsUseCase
import com.example.examten.domain.useCase.ValidateNumberUseCase
import com.example.examten.presentation.mapper.toPresentation
import com.example.examten.presentation.model.AccountUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToAccountViewModel @Inject constructor(private val validateNumberUseCase: ValidateNumberUseCase) :
    ViewModel() {

    private val _validationFlow = MutableSharedFlow<Resource<AccountUI>>()
    val validationFlow: SharedFlow<Resource<AccountUI>> = _validationFlow.asSharedFlow()


    fun validateAccountNumber(accountNum: String) {
        viewModelScope.launch {
            validateNumberUseCase.validate(accountNum).collect() {
                when (it) {
                    is Resource.Loading -> _validationFlow.emit(Resource.Loading())
                    is Resource.Success -> {
                        val res = it.responseData
                        if (res == null){
                            _validationFlow.emit(Resource.Success(null))
                        }
                        _validationFlow.emit(Resource.Success(it.responseData!!.toPresentation()))
                    }

                    is Resource.Failed -> _validationFlow.emit(Resource.Failed(it.message))
                }
            }
        }
    }
}