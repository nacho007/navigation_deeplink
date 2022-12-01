package com.test.androiddevelopersexample.ui.fragments.compose.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.GlobalPrimary300
import com.test.androiddevelopersexample.theme.GlobalPrimary400
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
internal fun Header(
    title: String,
    progress: Float,
    canGoForward: Boolean,
    canGoBack: Boolean,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        HeaderInfo(
            title = title,
            onClickPrevious = onClickPrevious,
            onClickNext = onClickNext,
            canGoBack = canGoBack,
            canGoForward = canGoForward,
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (progress / 100f < 1f) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                backgroundColor = GlobalPrimary300,
                color = GlobalPrimary400
            )
        }
    }
}

@Composable
private fun HeaderInfo(
    title: String,
    canGoForward: Boolean,
    canGoBack: Boolean,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        BodyText(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )

        Row {
            ArrowIndicator(
                imageVector = Icons.Filled.ChevronLeft,
                isEnable = canGoBack,
                onClick = {
                    onClickPrevious.invoke()
                }
            )

            ArrowIndicator(
                imageVector = Icons.Filled.ChevronRight,
                isEnable = canGoForward,
                onClick = {
                    onClickNext.invoke()
                }
            )
        }
    }
}

@Composable
private fun ArrowIndicator(isEnable: Boolean, imageVector: ImageVector, onClick: () -> Unit) {
    var modifier = Modifier
        .padding(8.dp)

    modifier = if (isEnable) {
        modifier
            .alpha(1f)
            .clickable { onClick.invoke() }
    } else {
        modifier.alpha(0.5f)
    }

    Icon(
        imageVector = imageVector,
        modifier = modifier,
        tint = Color.Black,
        contentDescription = null,
    )
}


@Composable
@Preview
private fun HeaderPreview() {
    AstroPayTheme {
        Header(
            title = "titulo",
            onClickPrevious = { },
            onClickNext = {},
            progress = 0.5f,
            canGoBack = true,
            canGoForward = true,
        )
    }
}