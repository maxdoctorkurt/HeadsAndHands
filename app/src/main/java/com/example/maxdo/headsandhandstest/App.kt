package com.example.maxdo.headsandhandstest

import android.app.Application
import com.example.maxdo.headsandhandstest.core.dagger.ApiCalls
import com.example.maxdo.headsandhandstest.core.dagger.appComponent.ContextModule
import com.example.maxdo.headsandhandstest.core.dagger.appComponent.DaggerAppComponent
import javax.inject.Inject

class App: Application() {

    @Inject
    lateinit var apiCalls: ApiCalls


    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent.builder().build()

        appComponent.inject(this)

        instance = this
    }


    companion object {
        var instance: App? = null
    }
}