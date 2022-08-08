package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey400
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun MyVouchersComponent(
    vouchers: List<String>,
    newVoucher: () -> Unit,
    seAllVoucher: () -> Unit,
    openVoucher: (id: String) -> Unit
) {
    Column {
        if (vouchers.isEmpty()) {
            HomeHeaderItem(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                text = "My vouchers"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                GenericVoucherItem(
                    color = Grey400,
                    action = { newVoucher() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.svg_plus_red),
                        contentDescription = "icon",
                        tint = Color.Unspecified
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    BodyText(
                        fontWeight = FontWeight.SemiBold,
                        text = "Buy your first voucher"
                    )
                    BodyText(text = "AstroPay")
                }
            }
        } else {
            HomeHeaderItem(
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                text = "My vouchers",
                action = { seAllVoucher() }
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    GenericVoucherItem(
                        color = Grey400,
                        text = "Buy voucher",
                        action = { newVoucher() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.svg_plus_red),
                            contentDescription = "icon",
                            tint = Color.Unspecified
                        )
                    }
                }
                items(vouchers.size) { index ->
                    GenericVoucherItem(
                        text = vouchers[index],
                        action = { openVoucher("id") }
                    ) {
                        BodyText(
                            fontWeight = FontWeight.Bold,
                            text = "AstroPay",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GenericVoucherItem(
    color: Color = Color.Black,
    text: String? = null,
    action: () -> Unit,
    cardContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.width(135.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .clickable { action() },
            contentAlignment = Alignment.Center
        ) {
            cardContent()
        }
        text?.let {
            Spacer(modifier = Modifier.height(4.dp))
            BodyText(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                text = it
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
private fun GenericVoucherItemPreview() {
    AstroPayTheme {
        Surface {
            GenericVoucherItem(
                text = "R$ 1000",
                action = { }
            ) {
                BodyText(
                    fontWeight = FontWeight.Bold,
                    text = "AstroPay",
                    color = Color.White
                )
            }
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
private fun VouchersPreview() {
    AstroPayTheme {
        Surface {
            val vouchers = listOf(
                "R$ 1000",
                "R$ 30",
                "R$ 5000",
                "R$ 58",
                "R$ 1000",
                "R$ 30",
                "R$ 5000",
                "R$ 58",
                "R$ 1000",
                "R$ 30",
                "R$ 5000",
                "R$ 58"
            )
            MyVouchersComponent(
                vouchers = vouchers,
                newVoucher = { },
                seAllVoucher = { },
                openVoucher = {

                }
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
private fun VouchersEmptyPreview() {
    AstroPayTheme {
        Surface {
            MyVouchersComponent(
                vouchers = emptyList(),
                newVoucher = { },
                seAllVoucher = { },
                openVoucher = {

                }
            )
        }
    }
}