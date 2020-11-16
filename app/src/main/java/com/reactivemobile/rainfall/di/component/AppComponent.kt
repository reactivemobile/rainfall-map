package com.reactivemobile.rainfall.di.component

import com.reactivemobile.rainfall.di.module.AppModule
import com.reactivemobile.rainfall.presentation.ui.details.StationDetailsFragment
import com.reactivemobile.rainfall.presentation.ui.map.StationsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: StationsFragment)
    fun inject(fragment: StationDetailsFragment)
}