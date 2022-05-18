package com.test.androiddevelopersexample.ui.fragments.more

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.FIREBASE_TOKEN
import com.test.androiddevelopersexample.databinding.FragmentMoreBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ErrorDialog
import com.test.androiddevelopersexample.ui.fragments.compose.ModalTransitionDialog
import com.test.androiddevelopersexample.ui.fragments.custom.ComposeIconButton
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_LOYALTY
import com.test.androiddevelopersexample.ui.utils.Messaging
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils
import com.test.androiddevelopersexample.ui.utils.navigate
import com.test.androiddevelopersexample.ui.utils.showToast
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {

    override var screenTag = "MoreFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            buttonsContainer.setContent {
                MaterialTheme {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))

                            var visible by rememberSaveable { mutableStateOf(false) }
                            ShowDialogWithAnimationButton(onClick = {
                                visible = true
                            })
                            ShowErrorDialog(visible) { visible = false }
                            CustomComponentButton()
                            CoilButton()
                            BadgesButton()
                            AstrocoinsButton()
                            NotificationLoyaltyButton()
                            NotificationArticleButton()
                            BottomSheetButton()
                            HelpCenterButton()
                            LogoutButton()
                            SwipeButton()
                            OnBoardingComposeButton()
                            CardsComposeButton()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ShowDialogWithAnimationButton(onClick: () -> Unit) {
        ComposeIconButton(text = stringResource(id = R.string.show_dialog), action = {
            onClick()
        })
    }

    @Composable
    private fun ShowErrorDialog(visible: Boolean, onClick: () -> Unit) {
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
            if (visible) {
                ModalTransitionDialog(onDismissRequest = { onClick() }) { modalTransitionDialogHelper ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {

                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = modalTransitionDialogHelper::triggerAnimatedClose
                        ) {
                            Text(
                                text = "Close"
                            )
                        }

                        Spacer(modifier = Modifier.size(32.dp))

                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Modal Transition Dialog"
                        )

                        Box(modifier = Modifier.fillMaxSize()) {
                            Button(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.color_black
                                    )
                                ),
                                onClick = modalTransitionDialogHelper::triggerAnimatedClose
                            ) {
                                Text(text = "Close it", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OnBoardingComposeButton() {
        ComposeIconButton(text = stringResource(id = R.string.onboarding_compose), action = {
            findNavController().navigate(R.id.action_moreFragment_to_OnBoardingFragment)
        })
    }

    @Composable
    private fun CardsComposeButton() {
        ComposeIconButton(text = stringResource(R.string.cards_list), action = {
            findNavController().navigate(R.id.action_moreFragment_to_cardsListFragment)
        })
    }

    @Composable
    private fun SwipeButton() {
        ComposeIconButton(text = stringResource(id = R.string.swipe), action = {
            navigate(R.id.action_moreFragment_to_swipeFragment)
        })
    }

    @Composable
    private fun LogoutButton() {
        ComposeIconButton(text = stringResource(id = R.string.logout), action = {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), false)
                ?.apply()

            (activity as NavigationActivity).showBottomNavigationMenu(false)
            (activity as NavigationActivity).logOut()
        })
    }

    @Composable
    private fun HelpCenterButton() {
        ComposeIconButton(text = stringResource(id = R.string.help_center), action = {
            navigate(R.id.action_moreFragment_to_fragmentHelpCenter)
        })
    }

    @Composable
    private fun BottomSheetButton() {
        ComposeIconButton(text = stringResource(id = R.string.bottom_sheet), action = {
            navigate(R.id.action_moreFragment_to_phoneBottomSheet)
        })
    }

    @Composable
    private fun NotificationLoyaltyButton() {
        val coroutineScope = rememberCoroutineScope()

        ComposeIconButton(
            text = stringResource(id = R.string.generate_push_notification_loyalty),
            action = {
                coroutineScope.launch {
                    invoke(
                        NotificationType.Loyalty(
                            text = "",
                            title = "Loyalty title",
                            type = PUSH_LOYALTY,
                            body = "Loyalty Body"
                        )
                    )
                }
            })
    }

    @Composable
    private fun NotificationArticleButton() {
        ComposeIconButton(
            text = stringResource(id = R.string.generate_push_notification_article),
            action = {


                val executor: ScheduledExecutorService =
                    Executors.newSingleThreadScheduledExecutor()
                executor.schedule({
                    PushNotificationUtils.createTradePushArticle(
                        requireContext(),
                        "Article title",
                        "Article Body"
                    )
                }, 1500.toLong(), TimeUnit.MILLISECONDS)


            })
    }

    @Composable
    private fun AstrocoinsButton() {
        ComposeIconButton(text = stringResource(id = R.string.astro_coins), action = {
            findNavController().navigate(R.id.action_moreFragment_to_astroCoinsFragment)
        })
    }

    @Composable
    private fun BadgesButton() {
        ComposeIconButton(
            text = stringResource(id = R.string.generate_badges), action = {
                (activity as NavigationActivity).createBadges(
                    R.id.newCardFragment,
                    2
                )
                (activity as NavigationActivity).createBadges(
                    R.id.loyaltyFragment,
                    3
                )
            },
            enabled = false
        )
    }

    @Composable
    private fun CoilButton() {
        var isNew by remember { mutableStateOf(true) }
        ComposeIconButton(
            text = stringResource(id = R.string.contacts_with_coil),
            isNew = isNew,
            action = {
                isNew = false
                findNavController().navigate(R.id.action_moreFragment_to_contactsFragment)
            })
    }

    @Composable
    private fun CustomComponentButton() {
        ComposeIconButton(text = stringResource(id = R.string.custom_component), action = {
            findNavController().navigate(
                R.id.action_moreFragment_to_fragmentCustomComponent
            )
        })
    }


    operator fun invoke(type: NotificationType) {
        val pushNotificationData = when (type) {
            is NotificationType.Loyalty -> {
                PushNotification.Message.Data(
                    type = type.type
                )
            }
        }

        val pushNotification = generatePushNotification(type, pushNotificationData)
        scheduleMessage(JsonParser().parse(Gson().toJson(pushNotification)).asJsonObject)
    }

    private fun generatePushNotification(
        type: NotificationType,
        data: PushNotification.Message.Data?
    ): PushNotification {
        return PushNotification(
            PushNotification.Message(
                requireActivity().getSharedPreferences(
                    getString(R.string.preferences), Context.MODE_PRIVATE
                ).getString(FIREBASE_TOKEN, ""),
                PushNotification.Message.Notification(type.title, type.body),
                data
            )
        )
    }

    private fun scheduleMessage(notification: JsonObject) {
        val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            Messaging.sendMessage(requireContext(), notification)
        }, PUSH_DEV_OPT_DELAY, TimeUnit.MILLISECONDS)
        showToast(PUSH_TIME_TOAST_TEXT, binding.root)
    }

    sealed class NotificationType {
        abstract val text: String
        abstract val title: String
        abstract val body: String
        abstract val type: String

        data class Loyalty(
            override val text: String = "Text",
            override val title: String = "Loyalty title",
            override val body: String = "Loyalty body",
            override val type: String = "LOYALTY"
        ) : NotificationType()
    }

    data class PushNotification(
        val message: Message,
    ) {
        data class Message(
            val token: String?,
            val notification: Notification,
            val data: Data?
        ) {
            data class Notification(
                val title: String,
                val body: String
            )

            data class Data(
                val type: String,
            )
        }
    }

    companion object {
        private const val SCREEN_NAME = "DEVELOPER_OPTIONS"
        private const val PUSH_DEV_OPT_DELAY = 3000L
        private const val PUSH_TIME_TOAST_TEXT = "Push will be sent in 3 seconds"
        private const val DIALOG = 103
    }
}
