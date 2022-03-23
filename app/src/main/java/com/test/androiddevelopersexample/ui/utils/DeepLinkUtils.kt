package com.test.androiddevelopersexample.ui.utils

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

object DeepLinkUtils {

    fun processPushData(bundle: Bundle): NavDeepLinkRequest? {
        return when (bundle.getString(PUSH_TYPE)) {
            PUSH_LOYALTY -> deepLinkLoyalty()
            PUSH_ARTICLE -> deepLinkArticle()
            else -> null
        }
    }

    fun createDeepLink(deepLink: String): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(deepLink.toUri())
            .build()
    }

    fun deepLinkArticle(): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(DEEP_LINK_ARTICLE.toUri())
            .build()
    }

    fun deepLinkLoyalty(): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(DEEP_LINK_LOYALTY.toUri())
            .build()
    }

    const val PUSH_TYPE = "type"

    const val PUSH_LOYALTY = "LOYALTY"
    const val PUSH_ARTICLE = "ARTICLE"


    const val DEEP_LINK_SIGN_UP = "app://signup"
    private const val DEEP_LINK_LOYALTY = "app://loyalty"
    private const val DEEP_LINK_ARTICLE = "app://article"
}
