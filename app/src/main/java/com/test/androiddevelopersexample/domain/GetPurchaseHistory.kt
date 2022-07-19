package com.test.androiddevelopersexample.domain

import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistory

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class GetPurchaseHistory(
    private val userRepository: UserRepository
) : BaseAction() {

    override val name: String get() = "GetPurchaseHistory"

    sealed class Result {
        data class Success(val value: List<PurchaseHistory>) : Result()
        data class Error(val value: ErrorResponse? = null) : Result()
        object NetworkError : Result()
    }

    suspend operator fun invoke(page: Int): Result {
        return when (val resultWrapper = userRepository.getPurchaseHistoryV2(page)) {
            is ResultWrapper.Success -> {
                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            is ResultWrapper.Error -> Result.Error(resultWrapper.error)
        }
    }
}
