package com.test.androiddevelopersexample.ui.fragments.compose.bottom_sheet

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.NestedScrollView

/**
 * Created by ignaciodeandreisdenis on 1/12/22.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(
    modifier: Modifier,
    title: String,
    url: String
) {
    Column {
        var webView by remember { mutableStateOf<WebView?>(null) }
        val scrollState = rememberScrollState()

        var progress: Float by remember { mutableStateOf(0f) }
        var canGoBack: Boolean by remember { mutableStateOf(false) }
        var canGoForward: Boolean by remember { mutableStateOf(false) }

        Header(
            title = title,
            onClickNext = {
                webView?.goForward()
            },
            onClickPrevious = {
                webView?.goBack()
            },
            progress = progress,
            canGoBack = canGoBack,
            canGoForward = canGoForward,
        )

        AndroidView(
            factory = { context ->
                val web = WebView(context).apply {
                    settings.run {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        builtInZoomControls = true
                        displayZoomControls = false
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            canGoForward = view?.canGoForward() ?: false
                            canGoBack = view?.canGoBack() ?: false
                            super.onPageFinished(view, url)
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                        }
                    }

                    webChromeClient = MyWebViewClient(updateProgress = {
                        progress = it.toFloat()
                    })

                    webView = this
                }

                NestedScrollView(context).apply {
                    addView(web)
                    web.loadUrl(url)
                }
            },
            modifier = modifier
                .height(IntrinsicSize.Max)
                .clipToBounds()
                .verticalScroll(scrollState)
        )
    }
}

private class MyWebViewClient(val updateProgress: (Int) -> Unit) : WebChromeClient() {
    override fun onProgressChanged(view: WebView, newProgress: Int) {
        updateProgress(newProgress)
        super.onProgressChanged(view, newProgress)
    }
}