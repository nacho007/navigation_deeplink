package com.test.androiddevelopersexample.infrastructure.network

import com.test.androiddevelopersexample.domain.models.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class ResponseHandler(
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun <T : Any> invoke(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                ResultWrapper.Error(null, null)
            }
        }
    }
}
