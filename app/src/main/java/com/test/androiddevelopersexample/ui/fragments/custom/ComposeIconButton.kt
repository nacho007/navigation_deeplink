package com.test.androiddevelopersexample.ui.fragments.custom

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.cardBackground
import com.test.androiddevelopersexample.theme.textColor
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun ComposeIconButton(
    text: String,
    icon: Painter = painterResource(R.drawable.ic_settings),
    enabled: Boolean = true,
    isNew: Boolean = false,
    action: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardBackground(),
            disabledBackgroundColor = cardBackground().copy(alpha = 0.5f),
            disabledContentColor = textColor().copy(alpha = 0.5f),
        ),
        onClick = action
    ) {
        Icon(
            painter = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        BodyText(
            modifier = Modifier
                .weight(1f, true)
                .padding(vertical = 8.dp)
                .align(alignment = Alignment.CenterVertically),
            color = if (enabled) textColor() else textColor().copy(alpha = 0.5f),
            text = text,
            style = TextStyle.Default.copy(
                fontWeight = FontWeight.Medium,
                color = if (enabled) Color.Black else Color.LightGray,
                fontSize = 16.sp
            )
        )
        if (isNew) {
            BodyText(
                modifier = Modifier
                    .background(color = Color.Red, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .align(alignment = Alignment.CenterVertically),
                text = stringResource(id = R.string.mobile_new),
                fontSize = 12.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            painter = painterResource(R.drawable.svg_arrow_right),
            contentDescription = null
        )
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
private fun IconButtonPreview() {
    AstroPayTheme {
        Surface {
            ComposeIconButton(
                text = "Text",
                icon = painterResource(id = R.drawable.svg_money),
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
private fun IconButtonDisablePreview() {
    AstroPayTheme {
        Surface {
            ComposeIconButton(
                text = "Text",
                icon = painterResource(id = R.drawable.svg_money),
                enabled = false,
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
private fun IconButtonNewPreview() {
    AstroPayTheme {
        Surface {
            ComposeIconButton(
                text = "Text",
                icon = painterResource(id = R.drawable.svg_money),
                isNew = true,
                action = {}
            )
        }
    }
}