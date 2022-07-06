package com.test.androiddevelopersexample.ui.fragments.compose.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.Grey800

/**
 * Created by ignaciodeandreisdenis on 6/7/22.
 */
@Composable
fun AstroCardView(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = if (isSystemInDarkTheme()) Grey800 else Color.White,
    contentColor: Color = MaterialTheme.colors.onSurface,
    border: BorderStroke? = null,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        content = content
    )
}