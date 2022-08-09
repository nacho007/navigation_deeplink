package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.GlobalPrimary400
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultTextButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.iconColorTint
import com.test.androiddevelopersexample.ui.fragments.compose.commons.images.AsyncImage
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun HomeToolbar(
    name: String?,
    image: String?,
    openProfile: () -> Unit,
    openNotifications: () -> Unit,
    counter: Int = 0
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    image = image,
                    placeholderRes = R.drawable.svg_user_profile_picture,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp)
                        .clickable { openProfile() },
                    contentScale = ContentScale.Companion.FillBounds
                )
                Spacer(modifier = Modifier.width(8.dp))
                BodyText(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { openProfile() }
                    ,
                    text = name ?: "",
                    fontSize = 16.sp
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = null,
        actions = {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = { openNotifications() }) {
                    Icon(
                        painter = painterResource(R.drawable.svg_notifications),
                        contentDescription = null,
                        tint = iconColorTint()
                    )
                }
                if (counter > 0) {
                    Box(
                        modifier = Modifier.size(22.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(12.dp)
                                .background(GlobalPrimary400)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun HomeHeaderItem(
    modifier: Modifier = Modifier,
    text: String,
    action: (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(
            modifier = Modifier
                .weight(1f),
            fontWeight = FontWeight.SemiBold,
            text = text
        )
        action?.let {
            DefaultTextButton(
                onClick = { it() },
                text = "See all",
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
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
private fun TopAppBarPreview() {
    AstroPayTheme {
        Surface {
            HomeToolbar(
                name = "Pedro Lopez",
                image = null,
                openProfile = { },
                openNotifications = {},
                counter = 0
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
private fun TopAppBarWithNotificationPreview() {
    AstroPayTheme {
        Surface {
            HomeToolbar(
                name = "Pedro Lopez",
                image = null,
                openProfile = { },
                openNotifications = {},
                counter = 1
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
private fun HomeHeaderItemPreview() {
    AstroPayTheme {
        Surface {
            HomeHeaderItem(
                text = "My transactions",
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
private fun HomeHeaderEmptyItemPreview() {
    AstroPayTheme {
        Surface {
            HomeHeaderItem(
                text = "My transactions",
                action = null
            )
        }
    }
}