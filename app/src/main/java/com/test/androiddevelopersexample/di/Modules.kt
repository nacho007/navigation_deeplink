package com.test.androiddevelopersexample.di

import com.test.androiddevelopersexample.domain.actions.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.repositories.UserRepository
import com.test.androiddevelopersexample.infrastructure.network.ResponseHandler
import com.test.androiddevelopersexample.infrastructure.paging_sources.PurchaseHistorySource
import com.test.androiddevelopersexample.infrastructure.repositories.UserRepositoryImpl
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.onboarding.OnboardingViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheetViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.EmployeeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistoryViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { OnboardingViewModel() }
    viewModel { ComposeViewModel() }
    viewModel { PhoneBottomSheetViewModel() }
    viewModel { PurchaseHistoryViewModel(get()) }
    viewModel { EmployeeViewModel() }
}

val repositoriesModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), androidContext()) }
    single { PurchaseHistorySource(get()) }
}

val actionsModule = module {
    single { GetPurchaseHistory(get()) }
}

val networkModule = module {
    single { ResponseHandler(Dispatchers.IO) }
}