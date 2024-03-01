package com.example.examten.di

import com.example.examten.BuildConfig
import com.example.examten.data.remote.service.AccountsService
import com.example.examten.data.remote.service.CurrencyService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideListRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_CLOTHES_LIST)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountsService(retrofit: Retrofit): AccountsService {
        return retrofit.create(AccountsService::class.java)


    }
    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)


    }

}