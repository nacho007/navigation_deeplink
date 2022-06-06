package com.test.androiddevelopersexample.ui.fragments.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R

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
            backgroundColor = Color.White,
            disabledBackgroundColor = Color.White,
            disabledContentColor = Color.LightGray
        ),
        onClick = action
    ) {
        Icon(
            painter = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier
                .weight(1f, true)
                .padding(vertical = 8.dp)
                .align(alignment = Alignment.CenterVertically),
            text = text,
            style = TextStyle.Default.copy(
                fontWeight = FontWeight.Medium,
                color = if (enabled) Color.Black else Color.LightGray,
                fontSize = 16.sp
            )
        )
        if (isNew) {
            Text(
                modifier = Modifier
                    .background(Color.Red)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .align(alignment = Alignment.CenterVertically),
                text = stringResource(id = R.string.mobile_new),
                style = TextStyle.Default.copy(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(
            painter = painterResource(R.drawable.svg_arrow_right),
            contentDescription = null
        )
    }
}