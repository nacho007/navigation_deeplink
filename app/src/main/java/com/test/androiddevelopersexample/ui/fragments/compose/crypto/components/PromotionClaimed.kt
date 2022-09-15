package com.test.androiddevelopersexample.ui.fragments.compose.crypto.components

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey900
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.loaders.LottieLoaderWithProgress
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.H4Title
import com.test.androiddevelopersexample.ui.fragments.compose.crypto.CryptoPromotionToClaim
import com.test.androiddevelopersexample.utils.DomainObjectsMocks

private const val CRYPTO_PROMOTION_CLAIMED_ANIMATION = "crypto_promotion_claimed.json"

private fun Modifier.cryptoPromotionAnimationSize(animationCompleted: Boolean): Modifier {
    return if (animationCompleted) Modifier.size(150.dp) else this
}

@Composable
fun PromotionClaimed(
    promotionToClaim: CryptoPromotionToClaim,
    cryptoImageBaseUrl: String,
    animationInitialState: Boolean = false
) {
    var animationCompleted by remember { mutableStateOf(animationInitialState) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Grey900.copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {

            val transition = updateTransition(targetState = animationCompleted, label = "transition")

            val rocketOffset by transition.animateOffset(transitionSpec = {
                if (this.targetState) {
                    tween(250) // launch duration

                } else {
                    tween(350) // land duration
                }
            }, label = "rocket offset") { animated ->
                if (animated)
                    Offset(0f, -30f)
                else Offset(0f, 0f)
            }

            LottieLoaderWithProgress(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(rocketOffset.x.dp, rocketOffset.y.dp),
                lottieResource = CRYPTO_PROMOTION_CLAIMED_ANIMATION
            ) {
                animationCompleted = true
            }

            AnimatedVisibility(visible = animationCompleted) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BodyText(
                        text = "You claimed:",
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                H4Title(text = promotionToClaim.crypto)

                                BodyText(
                                    text = "+ 0.0005",
                                    color = Color.Green
                                )
                            }

                            BodyText(
                                text = "+ ${promotionToClaim.fiatCurrency} ${promotionToClaim.fiatAmount}"
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Preview() {
    AstroPayTheme {
        PromotionClaimed(
            promotionToClaim = DomainObjectsMocks.getCryptoPromotionToClaim(),
            cryptoImageBaseUrl = "",
            animationInitialState = true
        )
    }
}