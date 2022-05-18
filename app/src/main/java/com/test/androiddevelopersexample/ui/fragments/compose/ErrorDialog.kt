package com.test.androiddevelopersexample.ui.fragments.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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