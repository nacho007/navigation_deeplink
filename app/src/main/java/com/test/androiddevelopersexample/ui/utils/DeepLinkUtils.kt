package com.test.androiddevelopersexample.ui.utils

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

object DeepLinkUtils {

    fun createDeepLink(deepLink: String): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(deepLink.toUri())
            .build()
    }

    fun deepLinkLoyalty(): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri("app://loyalty".toUri())
            .build()
    }
}
