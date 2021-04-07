package com.test.androiddevelopersexample.di.module

import com.test.androiddevelopersexample.ui.activities.MainDaggerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Philippe on 02/03/2018.
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainDaggerActivity?
}