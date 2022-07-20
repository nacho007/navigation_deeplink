package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.domain.User
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.cardBackground
import com.test.androiddevelopersexample.theme.dialogBackgroundColor
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import kotlinx.coroutines.flow.Flow
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class PaginatedFragment2 :
    BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    private val employeeViewModel: EmployeeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                AstroPayTheme {
                    UserList(viewModel = employeeViewModel, context = requireContext())
                }
            }
        }
    }

    @Composable
    fun EmployeeItem(empData: User, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(
                    bottom = 5.dp, top = 5.dp,
                    start = 5.dp, end = 5.dp
                )
                .fillMaxWidth()
                .clickable(onClick = onClick),
            backgroundColor = Color.Green,
            contentColor = Color.Red,
            shape = RoundedCornerShape(15.dp),
            elevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .height(350.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = empData.first_name,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 22.sp),
                        color = Color.Black
                    )
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.medium
                    ) {
                        Text(
                            text = empData.email,
                            style = typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(end = 25.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun UserList(modifier: Modifier = Modifier, viewModel: EmployeeViewModel, context: Context) {
        UserInfoList(modifier, userList = viewModel.user, context)
    }

    @Composable
    fun UserInfoList(modifier: Modifier, userList: Flow<PagingData<User>>, context: Context) {
        val userListItems: LazyPagingItems<User> = userList.collectAsLazyPagingItems()

        LazyColumn {
            items(userListItems) { item ->
                item?.let {
                    EmployeeItem(empData = it, onClick = {
                        Toast.makeText(context, item.id.toString(), Toast.LENGTH_SHORT).show()
                    })
                }
            }
            userListItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        //You can add modifier to manage load state when first time response page is loading
                    }
                    loadState.append is LoadState.Loading -> {
                        //You can add modifier to manage load state when next response page is loading
                    }
                    loadState.append is LoadState.Error -> {
                        //You can use modifier to show error message
                    }
                }
            }
        }
    }

    @Composable
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        locale = "es"
    )
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        locale = "es"
    )
    private fun PaginatedPreview() {
        AstroPayTheme {
            EmployeeItem(
                empData = User(
                    id = 1,
                    email = "ideandreis64@gmail.com",
                    first_name = "Ignacio",
                    last_name = "Deandreis",
                    avatar = "avatar"
                ),
                onClick = {}
            )
        }
    }
}
