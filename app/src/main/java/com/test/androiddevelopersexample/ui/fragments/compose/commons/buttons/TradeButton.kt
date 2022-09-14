package com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import java.util.*

@Composable
fun TradeBuyButton(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    enabled: Boolean = true,
) {

    TradeButton(
        modifier = modifier,
        text = stringResource(id = R.string.mobile_ok),
        startIcon = R.drawable.svg_wallet_crypto_outline_enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow,
            disabledBackgroundColor = Color.Yellow.copy(alpha = 0.1f)
        ),
        action = action,
        enabled = enabled
    )
}

@Composable
private fun TradeButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes startIcon: Int,
    action: () -> Unit,
    colors: ButtonColors,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        onClick = action,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled,
        colors = colors
    ) {
        BodyText(
            modifier = Modifier,
            text = text.uppercase(Locale.getDefault()),
            color = Color.Black,

            maxLines = 1
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun TradeBuyButtonPreview() {
    AstroPayTheme {
        TradeBuyButton(action = { })
        TradeBuyButton(action = { }, enabled = true)
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun TradeBuyButtonDisabledPreview() {
    AstroPayTheme {
        TradeBuyButton(action = { }, enabled = false)
        TradeBuyButton(action = { }, enabled = false)
    }
}

//@Composable
//@Preview(uiMode = UI_MODE_NIGHT_NO)
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//private fun TradeSellButtonPreview() {
//    AstroPayTheme {
//        TradeSellButton(action = { })
//        TradeSellButton(action = { }, enabled = false)
//    }
//}

@Composable
fun TradeSellButton(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    enabled: Boolean = true,
) {
    TradeButton(
        modifier = modifier,
        text = stringResource(id = R.string.mobile_cancel),
        startIcon = R.drawable.svg_dollar_sign,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            disabledBackgroundColor = Color.Red.copy(alpha = 0.3f)
        ),
        action = action,
        enabled = enabled
    )
}