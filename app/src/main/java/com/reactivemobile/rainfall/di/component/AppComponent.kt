package com.reactivemobile.rainfall.di.component

import com.reactivemobile.rainfall.di.module.AppModule
import com.reactivemobile.rainfall.presentation.ui.RainfallFragment
import com.reactivemobile.rainfall.presentation.ui.StationDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: RainfallFragment)
    fun inject(fragment: StationDetailsFragment)
}