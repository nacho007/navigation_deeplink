package com.test.androiddevelopersexample.ui.fragments.compose.commons

import android.content.res.Configuration
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.test.androiddevelopersexample.theme.AstroPayTheme

/**
 * Created by ignaciodeandreisdenis on 6/7/22.
 */
@Composable
fun AstroToolBar(
    title: String? = null,
    backgroundColor: Color? = null,
    items: List<MenuAction>? = null,
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            AstroText(text = title ?: "Text title")
        },
        backgroundColor = backgroundColor ?: toolBarBackgroundColor(),
        actions = {
            items?.let {
                if (items.isNotEmpty()) {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = textColor(),
                            modifier = Modifier.readDirection()
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                showMenu = false
                                it.action()
                            }) {
                                Text(it.title)
                            }
                        }
                    }
                }
            }
        },
        navigationIcon = navigationIcon,
        elevation = elevation
    )
}


data class MenuAction(
    val title: String,
    val action: () -> Unit
)

@Composable
fun IconNavigationBack(navAction: () -> Unit) {
    IconButton(onClick = navAction) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = iconColorTint(),
            modifier = Modifier.readDirection()
        )
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
fun ActionIconToolbarPreview() {
    AstroPayTheme {
        AstroToolBar(
            title = "Menu Toolbar Text",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = textColor(),
                        modifier = Modifier.readDirection()
                    )
                }
            }
        )
    }
}