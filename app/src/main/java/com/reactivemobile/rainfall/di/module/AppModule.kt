package com.reactivemobile.rainfall.di.module

import com.reactivemobile.rainfall.BuildConfig
import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import com.reactivemobile.rainfall.presentation.ui.RainfallViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRainfallClient(): RainfallClient {
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            //client.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        return Retrofit.Builder().baseUrl("https://environment.data.gov.uk")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RainfallClient::class.java)
    }

    @Provides
    @Singleton
    fun provideApiMapper(): ApiMapper = ApiMapper()

    @Provides
    @Singleton
    fun provideRepository(
        coroutineDispatcher: CoroutineDispatcher,
        rainfallClient: RainfallClient,
        apiMapper: ApiMapper
    ): RainfallRepository = RainfallRepository(coroutineDispatcher, rainfallClient, apiMapper)

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: RainfallRepository): RainfallViewModelFactory = RainfallViewModelFactory(repository)
}