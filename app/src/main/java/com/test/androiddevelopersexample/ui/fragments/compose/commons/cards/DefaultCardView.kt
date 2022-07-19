package com.test.androiddevelopersexample.ui.fragments.compose.commons.cards

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey800
import com.test.androiddevelopersexample.ui.fragments.compose.commons.text_field.DefaultDropdown
import com.test.androiddevelopersexample.ui.fragments.compose.commons.text_field.DefaultOutlinedTextField

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
@Composable
fun DefaultCardView(
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
private fun Preview() {
    AstroPayTheme {
        DefaultCardView(
            Modifier
                .padding(all = 16.dp)
        ) {
            Column(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultDropdown(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    label = "label",
                    suggestions = listOf("one", "two"),
                    selectedText = "two"
                ) { _, _ -> }
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    value = "Example",
                    onValueChange = {}
                )

                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                        .width(50.dp)
                )
            }
        }
    }
}