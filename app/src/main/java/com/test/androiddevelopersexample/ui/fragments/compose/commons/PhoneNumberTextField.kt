package com.test.androiddevelopersexample.ui.fragments.compose.commons

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Poppins

@Composable
fun PhoneNumberTextField(
    callingCode: String,
    flagIcon: String,
    hint: String,
    text: String,
    onTextChanged: (text: String) -> Unit,
    changeCountry: () -> Unit,
    onComplete: (() -> Unit)?
) {
    val hasFocus = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .border(
                border = BorderStroke(
                    if (hasFocus.value) 2.dp else 1.dp,
                    if (hasFocus.value) MaterialTheme.colors.onSurface else Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
/*        AsyncImage(
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    changeCountry()
                },
            model = flagIcon.plus(".svg"),
            contentDescription = "Country Flag"
        )*/

        Icon(
            modifier = Modifier.size(25.dp).clickable {
                changeCountry()
            },
            painter = painterResource(id = R.drawable.svg_camera),
            contentDescription = "Icon",
            tint = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.clickable {
                changeCountry()
            },
            text = callingCode,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged {
                    hasFocus.value = it.hasFocus
                },
            value = text,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    onTextChanged(it)
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onComplete?.let { it() } }
            ),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = hint,
                        color = Color.LightGray
                    )
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun PhoneNumberTextFieldPreview() {
    AstroPayTheme {
        PhoneNumberTextField(
            callingCode = "54",
            flagIcon = "",
            hint = "15XXXXXXX",
            text = "",
            onTextChanged = {},
            changeCountry = {},
            onComplete = {}
        )
    }
}