package com.test.androiddevelopersexample.application

import android.app.Application
import com.test.androiddevelopersexample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(): Unit {
        DaggerAppComponent.builder().application(this)?.build()?.inject(this)
    }
}
