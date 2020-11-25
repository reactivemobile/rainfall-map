package com.reactivemobile.rainfall.di.module

import android.content.Context
import com.reactivemobile.rainfall.data.database.RainfallDatabase
import com.reactivemobile.rainfall.data.database.dao.RainfallDao
import com.reactivemobile.rainfall.data.database.mapper.DbMapper
import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import com.reactivemobile.rainfall.presentation.ui.details.StationDetailsViewModelFactory
import com.reactivemobile.rainfall.presentation.ui.map.StationsViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideRainfallClient(): RainfallClient =
        Retrofit.Builder().baseUrl("https://environment.data.gov.uk")
            .client(OkHttpClient.Builder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RainfallClient::class.java)

    @Provides
    @Singleton
    fun provideRainfallDao(): RainfallDao = RainfallDatabase.create(context).rainfallDao()

    @Provides
    @Singleton
    fun provideApiMapper(): ApiMapper = ApiMapper()

    @Provides
    @Singleton
    fun provideDbMapper(): DbMapper = DbMapper()

    @Provides
    @Singleton
    fun provideRepository(
        rainfallClient: RainfallClient,
        apiMapper: ApiMapper,
        rainfallDao: RainfallDao,
        dbMapper: DbMapper
    ): RainfallRepository = RainfallRepository(
        rainfallClient,
        apiMapper,
        rainfallDao,
        dbMapper
    )

    @Provides
    @Singleton
    fun provideRainfallViewModelFactory(repository: RainfallRepository): StationsViewModelFactory = StationsViewModelFactory(repository)

    @Provides
    @Singleton
    fun provideStationDetailsViewModelFactory(repository: RainfallRepository): StationDetailsViewModelFactory = StationDetailsViewModelFactory(repository)
}