package com.test.androiddevelopersexample.di

import com.test.androiddevelopersexample.domain.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.UserRepository
import com.test.androiddevelopersexample.infrastructure.ResponseHandler
import com.test.androiddevelopersexample.infrastructure.UserRepositoryImpl
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.OnboardingViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.PhoneBottomSheetViewModel
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
}

val repositoriesModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), androidContext()) }
}

val actionsModule = module {
    single { GetPurchaseHistory(get()) }
}

val networkModule = module {
    single { ResponseHandler(Dispatchers.IO) }
}
