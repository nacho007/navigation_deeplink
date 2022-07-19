package com.test.androiddevelopersexample.di

import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.OnboardingViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.PhoneBottomSheetViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.history.PurchaseHistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { OnboardingViewModel() }
    viewModel { ComposeViewModel() }
    viewModel { PhoneBottomSheetViewModel() }
    viewModel { PurchaseHistoryViewModel() }
}