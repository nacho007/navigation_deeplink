package com.test.androiddevelopersexample.ui.fragments.compose.my_account

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.images.AsyncImage


@Composable
fun ProfilePicture(
    picture: String?,
    showEdit: Boolean = false,
    placeholderRes: Int = R.drawable.svg_user_profile_picture,
    icon: Int = R.drawable.svg_money,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
    ) {

        val modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)

        AsyncImage(
            image = picture,
            placeholderRes = placeholderRes,
            modifier = if (showEdit) {
                modifier.clickable {
                    onClick()
                }
            } else {
                modifier
            }
        )
        if (showEdit) {
            Box(
                modifier = Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .clickable {
                        onClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    modifier = Modifier.size(14.dp),
                    contentDescription = null,
                )
            }
        }
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
private fun ProfilePicturePreview() {
    AstroPayTheme {
        ProfilePicture(
            picture = "",
            showEdit = false
        )
    }
}