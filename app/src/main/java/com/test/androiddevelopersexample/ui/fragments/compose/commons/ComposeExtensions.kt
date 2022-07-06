package com.test.androiddevelopersexample.ui.fragments.compose.commons

import android.util.LayoutDirection
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.core.text.layoutDirection
import com.test.androiddevelopersexample.theme.Grey25
import com.test.androiddevelopersexample.theme.Grey800
import java.util.*

@Stable
fun Modifier.readDirection(): Modifier {
    return if (Locale.getDefault().layoutDirection == LayoutDirection.RTL)
        this.scale(scaleX = -1f, scaleY = 1f)
    else
        this
}

@Composable
fun toolBarColor() = if (isSystemInDarkTheme()) Grey800 else Grey25

@Composable
fun textColor() = if (isSystemInDarkTheme()) Color.White else Grey800

@Composable
fun iconColorTint() = if (isSystemInDarkTheme()) Color.White else Grey800

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
