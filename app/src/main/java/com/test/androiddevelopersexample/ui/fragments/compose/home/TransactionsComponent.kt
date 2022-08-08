package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey500
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun TransactionsComponent(
    modifier: Modifier = Modifier,
    transactions: List<String>,
    seAllTransactions: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (transactions.isEmpty()) {
            HomeHeaderItem(text = "My transactions")
            Spacer(modifier = Modifier.height(16.dp))
            DefaultCardView(
                modifier = Modifier.fillMaxWidth(),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(width = 60.dp, height = 55.dp),
                        painter = painterResource(id = R.drawable.svg_money),
                        contentDescription = "Icon"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BodyText(
                        text = "You don't have any transaction yet.",
                        color = if (isSystemInDarkTheme()) Color.White else Grey500
                    )
                }
            }
        } else {
            HomeHeaderItem(
                text = "My transactions",
                action = { seAllTransactions() }
            )
            Spacer(modifier = Modifier.height(8.dp))
            transactions.forEachIndexed { index, transaction ->
                if (index < 3) {
                    TransactionItem(
                        modifier = Modifier.padding(end = 8.dp),
                        text = transaction
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.svg_money),
            contentDescription = "icon"
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            BodyText(
                text = "Transfer to wallet",
                fontWeight = FontWeight.Medium
            )
            BodyText(text = "12 jun")
        }
        BodyText(text = text)
    }
    Spacer(modifier = Modifier.height(8.dp))
    Divider(modifier = modifier)
    Spacer(modifier = Modifier.height(16.dp))
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
private fun TransactionItemPreview() {
    AstroPayTheme {
        Surface {
            TransactionItem(
                text = "R$ 1000"
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
private fun TransactionsPreview() {
    AstroPayTheme {
        Surface {
            val transactions = listOf(
                "R$ 200",
                "BTC 0.03",
                "USD 45",
                "R$ 1000"
            )
            TransactionsComponent(
                transactions = transactions,
                seAllTransactions = { }
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
private fun TransactionsEmptyPreview() {
    AstroPayTheme {
        Surface {
            TransactionsComponent(
                transactions = emptyList(),
                seAllTransactions = { }
            )
        }
    }
}