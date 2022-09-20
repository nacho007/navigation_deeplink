package com.test.androiddevelopersexample.ui.fragments.compose.my_account

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.cardSecondaryBackground
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.SecondaryButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.images.AsyncImage
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText


@Composable
fun UserInfoComponent(
    userName: String,
    profileImage: String?,
    action: () -> Unit
) {
    DefaultCardView(
        color = cardSecondaryBackground(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfilePicture(picture = profileImage)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BodyText(
                    text = userName,
                    fontWeight = FontWeight.Bold
                )
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(1f),
                    action = { action() },
                    text = "Edit Profile"
                )
            }
        }
    }
}

@Composable
fun RewardsComponent(rewards: List<RewardsInformation>) {
    if (rewards.size > 1) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            rewards.forEach {
                RewardBox(
                    modifier = Modifier.weight(1f),
                    icon = it.image.icon,
                    title = it.title,
                    subTitle = it.subtitle,
                    action = {}
                )
            }
        }
    } else {
        RewardBox(
            modifier = Modifier,
            icon = rewards[0].image.icon,
            title = rewards[0].title,
            subTitle = rewards[0].subtitle,
            action = {}
        )
    }
}

@Composable
private fun RewardBox(
    modifier: Modifier,
    icon: Int = R.drawable.svg_user_profile_picture,
    image: String? = null,
    title: String,
    subTitle: String,
    action: () -> Unit
) {
    DefaultCardView(
        modifier = modifier,
        color = cardSecondaryBackground(),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { action() },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                image = null,
                placeholderRes = icon,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.FillBounds
            )
            Column {
                BodyText(text = title)
                BodyText(
                    text = subTitle,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

data class RewardsInformation(
    val image: BtnImage,
    val title: String,
    val subtitle: String
) {
    data class BtnImage(
        val icon: Int = R.drawable.svg_user_profile_picture,
        val url: String? = null,
    )
}

@Composable
fun ProfileCompletionComponent(
    modifier: Modifier,
    percentage: Int,
    action: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        var expanded by rememberSaveable { mutableStateOf(true) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = false },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = if (expanded) {
                Icons.Filled.ArrowDropUp
            } else {
                Icons.Filled.ArrowDropDown
            }
            BodyText(
                modifier = Modifier.weight(1f),
                text = "Profile Strength",
                fontWeight = FontWeight.SemiBold
            )
            Icon(imageVector = icon, contentDescription = "Icon")
        }
        AnimatedVisibility(visible = expanded) {
            DefaultCardView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                color = cardSecondaryBackground()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    val isCompleted = getFormattedPercentage(percentage) == 100
                    val text = if (isCompleted) "zaraza" else "azaasda dsa"
                    BodyText(
                        text = text,
                        fontWeight = FontWeight.SemiBold
                    )
                    // Percentage
                    if (isCompleted) {
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Complete you profile",
                            action = { action() }
                        )
                    }
                }
            }
        }
    }
}

private fun getFormattedPercentage(percentage: Int): Int {
    return if (percentage >= 100) 100 else percentage
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
private fun UserInfoComponentPreview() {
    AstroPayTheme {
        Surface {
            UserInfoComponent(
                userName = "Pepe",
                profileImage = null,
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
private fun RewardsComponentPreview() {
    AstroPayTheme {
        Surface {
            RewardsComponent(
                rewards = listOf(
                    RewardsInformation(
                        title = "Astrocoins",
                        subtitle = "2633",
                        image = RewardsInformation.BtnImage()
                    )
                )
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
private fun RewardsComponentTwoPreview() {
    AstroPayTheme {
        Surface {
            RewardsComponent(
                rewards = listOf(
                    RewardsInformation(
                        title = "Astrocoins",
                        subtitle = "2633",
                        image = RewardsInformation.BtnImage()
                    ),
                    RewardsInformation(
                        title = "Loyalty",
                        subtitle = "Platinum",
                        image = RewardsInformation.BtnImage()
                    )
                )
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
private fun ProfileCompletionPreview() {
    AstroPayTheme {
        Surface {
            ProfileCompletionComponent(
                modifier = Modifier,
                percentage = 90,
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
private fun ProfileCompletionCompletedPreview() {
    AstroPayTheme {
        Surface {
            ProfileCompletionComponent(
                modifier = Modifier,
                percentage = 100,
                action = {}
            )
        }
    }
}