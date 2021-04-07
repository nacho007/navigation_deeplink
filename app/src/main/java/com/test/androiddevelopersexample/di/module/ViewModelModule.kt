package com.test.androiddevelopersexample.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.androiddevelopersexample.di.key.ViewModelKey
import com.test.androiddevelopersexample.viewmodel.FactoryViewModel
import com.test.androiddevelopersexample.viewmodel.UserProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileViewModel(repoViewModel: UserProfileViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: FactoryViewModel): ViewModelProvider.Factory
}
