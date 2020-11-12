package com.reactivemobile.rainfall.application

import android.app.Application
import com.reactivemobile.rainfall.di.component.AppComponent
import com.reactivemobile.rainfall.di.component.DaggerAppComponent

class RainfallApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}