package com.test.androiddevelopersexample.di

import com.test.androiddevelopersexample.ui.fragments.compose.OnboardingViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    single { OnboardingViewModel() }
}