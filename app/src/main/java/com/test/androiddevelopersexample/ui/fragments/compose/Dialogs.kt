package com.test.androiddevelopersexample.ui.fragments.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R

/**
 * Created by ignaciodeandreisdenis on 18/5/22.
 */

@Composable
fun ErrorDialog(
    onConfirm: () -> Unit,
    desc: String
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onConfirm)
            {
                Text(
                    text = stringResource(id = R.string.mobile_ok),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onConfirm)
            {
                Text(
                    text = stringResource(id = R.string.mobile_cancel),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.mobile_error),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = desc,
                color = MaterialTheme.colors.onSurface,
            )
        }
    )
}

@Composable
fun CustomDialog(
    description: String,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .background(colorResource(R.color.color_balloon), RoundedCornerShape(4.dp))
    ) {

        Text(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.mobile_error),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(onClick = onConfirm)
            {
                Text(
                    text = stringResource(id = R.string.mobile_ok),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun CustomPositiveNegativeDialog(
    title: String,
    description: String,
    positiveText: String,
    negativeText: String,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .background(colorResource(R.color.color_balloon), RoundedCornerShape(4.dp))
    ) {

        Text(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = title,
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onNegative)
            {
                Text(
                    text = negativeText,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }

            TextButton(onClick = onPositive)
            {
                Text(
                    text = positiveText,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowPreview() {
    ErrorDialog(
        onConfirm = {},
        desc = "Error description",
    )
}

@Preview
@Composable
fun ShowPreview2() {
    CustomDialog(
        description = "Error",
        onConfirm = {}
    )
}

@Preview
@Composable
fun ShowPreview3() {
    CustomPositiveNegativeDialog(
        title = "Title",
        description = "Do you wish to use Touch ID to log in? You can always change this in Settings",
        positiveText = "Yes",
        negativeText = "No",
        onPositive = { /*TODO*/ }) {
    }
}
