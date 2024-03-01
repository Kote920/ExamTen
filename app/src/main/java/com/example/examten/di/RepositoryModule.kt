package com.example.examten.di

import com.example.examten.data.remote.service.AccountsService
import com.example.examten.data.remote.service.CurrencyService
import com.example.examten.data.repository.AccountsRepositoryImpl
import com.example.examten.data.repository.CurrencyRepositoryImpl
import com.example.examten.domain.repository.AccountsRepository
import com.example.examten.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAccountsRepository(
        accountsService: AccountsService
    ): AccountsRepository {
        return AccountsRepositoryImpl(
            accountsService
        )
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(
       currencyService: CurrencyService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            currencyService
        )
    }
}