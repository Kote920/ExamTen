package com.example.examten.presentation.mapper

import com.example.examten.domain.model.Currency
import com.example.examten.presentation.model.CurrencyUI

fun Currency.toPresentation() = CurrencyUI(rate = rate)