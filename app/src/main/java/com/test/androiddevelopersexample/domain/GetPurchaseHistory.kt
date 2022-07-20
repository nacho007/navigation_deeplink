package com.test.androiddevelopersexample.domain

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class GetPurchaseHistory(
    private val userRepository: UserRepository
) : BaseAction() {

    override val name: String get() = "GetPurchaseHistory"

    sealed class Result {
        data class Success(val value: PurchaseHistoryResultV2) : Result()
        data class Error(val value: ErrorResponse? = null) : Result()
        object NetworkError : Result()
    }

    suspend operator fun invoke(page: Int): Result {
        return when (val resultWrapper = userRepository.getPurchaseHistoryV2(page)) {
            is ResultWrapper.Success -> {
                resultWrapper.value.purchaseHistories.map {
                    it.image =
                        "https://getapp-test.astropaycard.com/img/payment_methods/".plus(it.image)
                    it
                }

                Result.Success(resultWrapper.value)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError
            is ResultWrapper.Error -> Result.Error(resultWrapper.error)
        }
    }
}
