package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultTextButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun HomeHeaderItem(
    modifier: Modifier = Modifier,
    text: String,
    action: (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(
            modifier = Modifier
                .weight(1f),
            fontWeight = FontWeight.SemiBold,
            text = text
        )
        action?.let {
            DefaultTextButton(
                onClick = { it() },
                text = "See all",
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            )
        }
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en"
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en"
)
private fun HomeHeaderItemPreview() {
    AstroPayTheme {
        Surface {
            HomeHeaderItem(
                text = "My activity",
                action = {}
            )
        }
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en"
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en"
)
private fun HomeHeaderEmptyItemPreview() {
    AstroPayTheme {
        Surface {
            HomeHeaderItem(
                text = "My activity",
                action = null
            )
        }
    }
}