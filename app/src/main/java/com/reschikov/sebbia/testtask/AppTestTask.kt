package com.reschikov.sebbia.testtask

import android.app.Application
import com.reschikov.sebbia.testtask.di.AppComponent
import com.reschikov.sebbia.testtask.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppTestTask : Application(), HasAndroidInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        createAppComponent().inject(this)
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .getContext(applicationContext)
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}