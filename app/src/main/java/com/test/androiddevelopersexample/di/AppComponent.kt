package com.test.androiddevelopersexample.di


import android.app.Application
import com.test.androiddevelopersexample.application.MyApplication
import com.test.androiddevelopersexample.di.module.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityModule::class, AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

    fun inject(app: MyApplication?)
}