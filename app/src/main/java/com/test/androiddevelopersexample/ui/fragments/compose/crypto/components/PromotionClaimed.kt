package com.test.androiddevelopersexample.ui.fragments.compose.crypto.components

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey900
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.image.DefaultAsyncImage
import com.test.androiddevelopersexample.ui.fragments.compose.commons.loaders.LottieLoaderWithProgress
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.H4Title
import com.test.androiddevelopersexample.ui.fragments.compose.crypto.CASHBACK_PROMO_CRYPTO_CURRENCY_TEST
import com.test.androiddevelopersexample.ui.fragments.compose.crypto.CryptoPromotionToClaim
import com.test.androiddevelopersexample.ui.utils.ImageExtension
import com.test.androiddevelopersexample.ui.utils.PlaceHolderType
import com.test.androiddevelopersexample.utils.DomainObjectsMocks
import androidx.activity.compose.BackHandler

private const val CRYPTO_PROMOTION_CLAIMED_ANIMATION = "crypto_promotion_claimed.json"


@Composable
fun PromotionClaimed(
    promotionToClaim: CryptoPromotionToClaim,
    cryptoImageBaseUrl: String,
    animationInitialState: Boolean = false,
    onClick: () -> Unit
) {
    var animationCompleted by remember { mutableStateOf(animationInitialState) }

    var backPressedCount by remember { mutableStateOf(0) }
    BackHandler(enabled = true, onBack = {
        backPressedCount += 1
    })
    Text(text="Backbutton was pressed : $backPressedCount times")

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Grey900.copy(alpha = 0.8f)
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .clickable(onClick = onClick)
            )

            val transition =
                updateTransition(targetState = animationCompleted, label = "transition")

            val cryptoImageOffset by transition.animateOffset(transitionSpec = {
                if (this.targetState) {
                    tween(250) // launch duration

                } else {
                    tween(350) // land duration
                }
            }, label = "crypto offset") { animated ->
                if (animated)
                    Offset(0f, -55f)
                else Offset(0f, 0f)
            }

            LottieLoaderWithProgress(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(cryptoImageOffset.x.dp, cryptoImageOffset.y.dp),
                lottieResource = CRYPTO_PROMOTION_CLAIMED_ANIMATION
            ) {
                animationCompleted = true
            }

            AnimatedVisibility(visible = animationCompleted) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BodyText(
                        text = "You claimed:",
                        color = Color.White
                    )

                    DefaultCardView(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DefaultAsyncImage(
                                    modifier = Modifier
                                        .size(36.dp),
                                    image = cryptoImageBaseUrl + CASHBACK_PROMO_CRYPTO_CURRENCY_TEST,
                                    placeholderRes = PlaceHolderType.CUBE.resource,
                                    imageExtension = ImageExtension.SVG
                                )

                                Spacer(Modifier.width(16.dp))

                                H4Title(
                                    text = "+ ".plus(promotionToClaim.crypto).plus(" 0.0005"),
                                    color = Color.Green
                                )
                            }

                            BodyText(
                                text = "+ ${promotionToClaim.fiatCurrency} ${promotionToClaim.fiatAmount}"
                            )
                        }
                    }

                    BodyText(
                        text = "Your cashback will be reflected in your balance",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun BackHandlerExample() {
    var backPressedCount by remember { mutableStateOf(0) }
    BackHandler(enabled = true, onBack = {
        backPressedCount += 1
    })
    Text(text="Backbutton was pressed : $backPressedCount times")
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Preview() {
    AstroPayTheme {
        PromotionClaimed(
            promotionToClaim = DomainObjectsMocks.getCryptoPromotionToClaim(),
            cryptoImageBaseUrl = "",
            animationInitialState = true,
            onClick = {}
        )
    }
}