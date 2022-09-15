package com.test.androiddevelopersexample.utils

import com.test.androiddevelopersexample.ui.fragments.compose.crypto.CryptoPromotionToClaim

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
object DomainObjectsMocks {
    fun getCryptoPromotionToClaim(
        promoId: Int = 301,
        percentage: Double = 3.00,
        promoType: String = "CASHBACK_BTC",
        crypto: String = "BTC",
        fiatCurrency: String = "USD",
        fiatAmount: Double = 50.00,
    ) = CryptoPromotionToClaim(
        promoId = promoId,
        percentage = percentage,
        promoType = promoType,
        crypto = crypto,
        fiatCurrency = fiatCurrency,
        fiatAmount = fiatAmount
    )
}
