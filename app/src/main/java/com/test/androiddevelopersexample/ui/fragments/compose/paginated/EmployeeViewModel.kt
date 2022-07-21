package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.androiddevelopersexample.domain.user.User
import com.test.androiddevelopersexample.infrastructure.paging_sources.UserSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class EmployeeViewModel : ViewModel() {
    val user: Flow<PagingData<User>> = Pager(
        PagingConfig(
            pageSize = 6,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        UserSource()
    }.flow.cachedIn(viewModelScope)
}
