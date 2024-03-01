package com.example.examten.presentation.mapper

import com.example.examten.domain.model.Account
import com.example.examten.presentation.model.AccountUI

fun Account.toPresentation() = AccountUI(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
    cardLogo = cardLogo
)