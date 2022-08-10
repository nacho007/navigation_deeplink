package com.test.androiddevelopersexample.ui.fragments.compose.contacts.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import androidx.compose.foundation.layout.*
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.images.AsyncImage
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.setSubstringWithStyle

@Composable
fun ContactItem(
    name: String?,
    number: String,
    picture: String?,
    searchText: String,
    isRecent: Boolean = false,
    onClick: () -> Unit,
) {
    DefaultCardView(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(16.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                image = picture,
                placeholderRes = R.drawable.svg_user_profile_picture,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                name?.let {
                    BodyText(
                        text = name.setSubstringWithStyle(
                            searchText,
                            SpanStyle(color = MaterialTheme.colors.primary)
                        ),
                        fontWeight = if (isRecent) FontWeight.SemiBold else FontWeight.Normal
                    )
                }

                BodyText(
                    text = number.setSubstringWithStyle(
                        searchText,
                        SpanStyle(color = MaterialTheme.colors.primary)
                    )
                )
            }

            if (isRecent) {
                BodyText(
                    text = stringResource(id = R.string.mobile_recents),
                    fontSize = 12.sp
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
private fun ContactItemPreview() {
    AstroPayTheme {
        ContactItem(
            name = "Mairon Andres Piedrahita",
            number = "3155555555",
            picture = null,
            searchText = "55"
        ) {}
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
private fun ContactItemRecentPreview() {
    AstroPayTheme {
        ContactItem(
            name = "Mairon Andres Piedrahita",
            number = "3155555555",
            picture = null,
            searchText = "piedra",
            isRecent = true
        ) {}
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
private fun ContactItemRecentNameNullPreview() {
    AstroPayTheme {
        ContactItem(
            name = null,
            number = "3155555555",
            picture = null,
            searchText = "piedra",
            isRecent = true
        ) {}
    }
}