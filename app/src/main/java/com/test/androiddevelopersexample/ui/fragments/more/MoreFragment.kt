package com.test.androiddevelopersexample.ui.fragments.more

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.test.androiddevelopersexample.ui.fragments.compose.CustomDialog
import com.test.androiddevelopersexample.ui.fragments.compose.CustomPositiveNegativeDialog
import com.test.androiddevelopersexample.ui.fragments.compose.ErrorDialog
import com.test.androiddevelopersexample.ui.fragments.compose.dialogs.ModalTransitionDialog
import com.test.androiddevelopersexample.ui.fragments.custom.ComposeIconButton
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_LOYALTY
import com.test.androiddevelopersexample.ui.utils.Messaging
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils
import com.test.androiddevelopersexample.ui.utils.navigate
import com.test.androiddevelopersexample.ui.utils.showToast
import kotlinx.coroutines.launch
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

                            ComposeFragment()

                            var visibleAlertDialog by rememberSaveable { mutableStateOf(false) }
                            var visibleErrorDialog by rememberSaveable { mutableStateOf(false) }
                            var visiblePositiveNegativeDialog by rememberSaveable {
                                mutableStateOf(
                                    false
                                )
                            }

                            AlertDialogButton(onClick = {
                                visibleAlertDialog = true
                            })

                            CustomErrorDialogButton(onClick = {
                                visibleErrorDialog = true
                            })

                            CustomPositiveNegativeDialogButton(onClick = {
                                visiblePositiveNegativeDialog = true
                            })

                            ShowAlertErrorDialog(visibleAlertDialog) { visibleAlertDialog = false }
                            ShowCustomErrorDialog(visibleErrorDialog) { visibleErrorDialog = false }
                            ShowCustomPositiveNegativeDialog(visiblePositiveNegativeDialog) {
                                visiblePositiveNegativeDialog = false
                            }
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
                            Spacer(modifier = Modifier.height(65.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeFragment() {
        ComposeIconButton(text = stringResource(R.string.compose_fragment), action = {
            findNavController().navigate(R.id.action_moreFragment_to_composeFragment)
        })
    }

    @Composable
    private fun CustomErrorDialogButton(onClick: () -> Unit) {
        ComposeIconButton(text = stringResource(id = R.string.show_custom_error_dialog), action = {
            onClick()
        })
    }

    @Composable
    private fun AlertDialogButton(onClick: () -> Unit) {
        ComposeIconButton(text = stringResource(id = R.string.show_alert_dialog), action = {
            onClick()
        })
    }

    @Composable
    private fun CustomPositiveNegativeDialogButton(onClick: () -> Unit) {
        ComposeIconButton(
            text = stringResource(id = R.string.show_custom_positive_negative_dialog),
            action = {
                onClick()
            })
    }

    @Composable
    private fun ShowAlertErrorDialog(visible: Boolean, onClick: () -> Unit) {
        if (visible) {
            ErrorDialog(onConfirm = { onClick() }, desc = "My description")
        }
    }

    @Composable
    private fun ShowCustomErrorDialog(visible: Boolean, onClick: () -> Unit) {
        if (visible) {
            ModalTransitionDialog(onDismissRequest = { onClick() }) { modalTransitionDialogHelper ->
                CustomDialog(
                    description = "My description",
                    onConfirm = modalTransitionDialogHelper::triggerAnimatedClose,
                )
            }
        }
    }

    @Composable
    private fun ShowCustomPositiveNegativeDialog(visible: Boolean, onClick: () -> Unit) {
        if (visible) {
            ModalTransitionDialog(onDismissRequest = { onClick() }) { modalTransitionDialogHelper ->
                CustomPositiveNegativeDialog(
                    title = "Title",
                    description = "Do you wish to use Touch ID to log in? You can always change this in Settings",
                    positiveText = "Yes",
                    negativeText = "No",
                    onPositive = {
                        Log.e(screenTag, "Positive")
                        modalTransitionDialogHelper.triggerAnimatedClose()
                    }) {
                    Log.e(screenTag, "Negative")
                    modalTransitionDialogHelper.triggerAnimatedClose()
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
