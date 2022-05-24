package com.test.androiddevelopersexample.di

import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.OnboardingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { OnboardingViewModel() }
    viewModel { ComposeViewModel() }
}