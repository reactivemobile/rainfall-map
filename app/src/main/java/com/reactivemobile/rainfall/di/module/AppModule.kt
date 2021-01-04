package com.reactivemobile.rainfall.di.module

import android.content.Context
import com.reactivemobile.rainfall.data.database.RainfallDatabase
import com.reactivemobile.rainfall.data.database.dao.RainfallDao
import com.reactivemobile.rainfall.data.database.mapper.DbMapper
import com.reactivemobile.rainfall.data.network.client.RainfallClient
import com.reactivemobile.rainfall.data.network.mapper.ApiMapper
import com.reactivemobile.rainfall.domain.repository.RainfallRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(
    ActivityRetainedComponent::class
)
class AppModule() {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideRainfallClient(): RainfallClient =
        Retrofit.Builder().baseUrl("https://environment.data.gov.uk")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RainfallClient::class.java)

    @Provides
    fun provideRainfallDao(@ApplicationContext context: Context): RainfallDao = RainfallDatabase.create(context).rainfallDao()

    @Provides
    fun provideApiMapper(): ApiMapper = ApiMapper()

    @Provides
    fun provideDbMapper(): DbMapper = DbMapper()

    @Provides
    fun provideRepository(
        coroutineDispatcher: CoroutineDispatcher,
        rainfallClient: RainfallClient,
        apiMapper: ApiMapper,
        rainfallDao: RainfallDao,
        dbMapper: DbMapper
    ): RainfallRepository = RainfallRepository(
        coroutineDispatcher,
        rainfallClient,
        apiMapper,
        rainfallDao,
        dbMapper
    )
}