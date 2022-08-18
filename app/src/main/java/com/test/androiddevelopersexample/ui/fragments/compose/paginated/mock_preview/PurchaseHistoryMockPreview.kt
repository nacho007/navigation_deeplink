package com.test.androiddevelopersexample.ui.fragments.compose.paginated.mock_preview

import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistoryViewModel

object PurchaseHistoryMockPreview {

    internal fun getMockState() = PurchaseHistoryViewModel.ViewState(
        loadState = Type.SHOW_CONTENT,
    )
}