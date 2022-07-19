package com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey25
import com.test.androiddevelopersexample.theme.Grey900
import com.test.androiddevelopersexample.theme.secondaryButtonColor
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 7/7/22.
 */

@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    action: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = action,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
        ),
        enabled = enabled
    ) {
        BodyText(
            text = text.uppercase(Locale.getDefault()),
            color = Color.White
        )
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier,
    text: String,
    action: () -> Unit,
    enabled: Boolean = true,
    color: Color = Color.Unspecified
) {
    Button(
        modifier = modifier,
        border = BorderStroke(
            2.dp,
            MaterialTheme.colors.primary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (color == Color.Unspecified) secondaryButtonColor() else color
        ),
        onClick = action,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {
        BodyText(
            text = text.uppercase(Locale.getDefault()),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun DefaultTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colors.primaryVariant
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    text: String
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "es"
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "es"
)
private fun Previews() {
    AstroPayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = "Continues",
                action = {}
            )

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = "Continues",
                enabled = false,
                action = {}
            )

            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = "Secondary Button",
                action = {},
                color = if (isSystemInDarkTheme()) Grey900 else Grey25
            )

            DefaultTextButton(
                text = "Default Text Button",
                onClick = {})
        }
    }
}