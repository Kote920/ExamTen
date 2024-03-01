package com.example.examten.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examten.data.common.Resource
import com.example.examten.domain.useCase.GetCurrencyUseCase
import com.example.examten.domain.useCase.ValidateNumberUseCase
import com.example.examten.presentation.mapper.toPresentation
import com.example.examten.presentation.model.AccountUI
import com.example.examten.presentation.model.CurrencyUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel@Inject constructor(private val getCurrencyUseCase: GetCurrencyUseCase) :
    ViewModel() {

    private val _currencyFlow = MutableSharedFlow<Resource<CurrencyUI>>()
    val currencyFlow: SharedFlow<Resource<CurrencyUI>> = _currencyFlow.asSharedFlow()


    fun getCurrency() {
        viewModelScope.launch {
            getCurrencyUseCase.invoke().collect() {
                when (it) {
                    is Resource.Loading -> _currencyFlow.emit(Resource.Loading())
                    is Resource.Success -> {
                      _currencyFlow.emit(Resource.Success(it.responseData!!.toPresentation()))
                    }

                    is Resource.Failed -> _currencyFlow.emit(Resource.Failed(it.message))
                }
            }
        }
    }
}