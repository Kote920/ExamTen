package com.example.examten.di

import com.example.examten.domain.repository.AccountsRepository
import com.example.examten.domain.repository.CurrencyRepository
import com.example.examten.domain.useCase.GetAccountsUseCase
import com.example.examten.domain.useCase.GetCurrencyUseCase
import com.example.examten.domain.useCase.ValidateNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAccountsUseCase(accountsRepository: AccountsRepository): GetAccountsUseCase {
        return GetAccountsUseCase(accountsRepository)
    }

    @Singleton
    @Provides
    fun provideValidateNumberUseCase(accountsRepository: AccountsRepository): ValidateNumberUseCase {
        return ValidateNumberUseCase(accountsRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrencyUseCase(currencyRepository: CurrencyRepository): GetCurrencyUseCase {
        return GetCurrencyUseCase(currencyRepository)
    }


}