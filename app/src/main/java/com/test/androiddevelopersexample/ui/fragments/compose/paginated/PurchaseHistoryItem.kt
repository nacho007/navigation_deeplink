package com.test.androiddevelopersexample.ui.fragments.compose.paginated

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.images.AstroAsyncImage
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Status.APPROVED
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Status.CANCELED
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Status.CANCELLED
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Status.COMPLETED
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Status.PENDING
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Type.APC
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Type.PREPAID_CARD
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2.Type.WALLET_BALANCE
import com.test.androiddevelopersexample.ui.utils.DateUtils
import com.test.androiddevelopersexample.ui.utils.ImageExtension
import com.test.androiddevelopersexample.ui.utils.Utils

@Composable
fun PurchaseHistoryItem(
    image: String?,
    type: PurchaseHistoryV2.Type,
    name: String,
    status: PurchaseHistoryV2.Status,
    currency: String,
    amount: Double,
    date: String,
    onClick: () -> Unit
) {
    DefaultCardView(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(16.dp)

            ) {
                Row(Modifier.weight(0.5f)) {
                    AstroAsyncImage(
                        image = image,
                        placeholderRes = R.drawable.svg_loading_cube,
                        imageExtension = ImageExtension.SVG,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically),
                    )
                    Column(
                        Modifier.padding(start = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(0.66f),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            BodyText(
                                text = stringResource(
                                    id = when (type) {
                                        APC -> R.string.mobile_new_card
                                        WALLET_BALANCE -> R.string.mobile_wallet
                                        PREPAID_CARD -> R.string.mobile_game_cards
                                    }
                                ),
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                            )
                            BodyText(
                                text = name,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Box(
                            modifier = Modifier.weight(0.33f),
                            contentAlignment = Alignment.TopStart
                        ) {
                            BodyText(
                                text = stringResource(
                                    id = when (status) {
                                        PENDING -> R.string.mobile_pending
                                        CANCELED, CANCELLED -> R.string.mobile_canceled
                                        COMPLETED, APPROVED -> R.string.mobile_completed
                                    }
                                ),
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(0.5f),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        BodyText(
                            text = stringResource(
                                id = R.string.two_values,
                                currency,
                                Utils.decimalFormatTwoDecimal?.format(amount).toString()
                            ),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(
                                id = when (status) {
                                    PENDING -> R.color.color_yellow
                                    CANCELED, CANCELLED -> R.color.color_red
                                    COMPLETED, APPROVED -> R.color.color_ok
                                }
                            ),
                        )
                    }
                    Box(
                        modifier = Modifier.weight(0.5f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        BodyText(
                            text = DateUtils.instance.getWalletActivityFormat(date)
                                .replaceFirst(" ", "\n"),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}


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
@Composable
private fun PurchaseHistoryItemPreview() {
    AstroPayTheme {
        PurchaseHistoryItem(
            image = null,
            type = APC,
            name = "Santander",
            status = PENDING,
            currency = "$",
            amount = 1000.0,
            date = "2021-11-16T17:36:58Z"
        ) {}
    }
}