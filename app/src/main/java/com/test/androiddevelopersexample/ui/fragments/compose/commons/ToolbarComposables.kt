package com.test.androiddevelopersexample.ui.fragments.compose.commons

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

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
            BodyText(text = title ?: "Text title")
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

@ExperimentalComposeUiApi
@Composable
fun SearchToolbar(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val icon = if (showClearButton) Icons.Filled.Close else Icons.Filled.Search

    TopAppBar(
        title = {
            Text(text = "")
        },
        backgroundColor = toolBarBackgroundColor(),
        navigationIcon = navigationIcon,
        actions = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused)
                    }
                    .focusRequester(focusRequester),
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = {
                    Text(text = placeholderText)
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        if (showClearButton) onClearClick() else focusRequester.requestFocus()
                    }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "search..."
                        )
                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
            )
        }
    )

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

@OptIn(ExperimentalComposeUiApi::class)
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
fun ActionIconToolbarSearchPreview() {
    AstroPayTheme {
        SearchToolbar(
            placeholderText = "Search text",
            searchText = "",
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