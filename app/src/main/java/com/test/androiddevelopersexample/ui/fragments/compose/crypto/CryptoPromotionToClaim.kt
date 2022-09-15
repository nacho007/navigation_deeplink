package com.test.androiddevelopersexample.ui.fragments.compose.crypto

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
data class CryptoPromotionToClaim(
    val promoId: Int,
    val percentage: Double,
    val promoType: String,
    val crypto: String = CASHBACK_PROMO_CRYPTO_CURRENCY,
    val fiatCurrency: String,
    val fiatAmount: Double
)

private const val CASHBACK_PROMO_CRYPTO_CURRENCY = "BTC"
const val CASHBACK_PROMO_CRYPTO_CURRENCY_TEST = "BTC_TEST"