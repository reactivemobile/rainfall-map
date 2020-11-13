package com.reactivemobile.rainfall.application

import android.app.Application
import com.reactivemobile.rainfall.di.component.AppComponent
import com.reactivemobile.rainfall.di.component.DaggerAppComponent
import com.reactivemobile.rainfall.di.module.AppModule

class RainfallApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}