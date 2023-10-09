package rootBottomStack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import feeds.FeedsContent
import message.MessageContent
import notifications.NotificationContent
import welcome.WelcomeContent
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootBottomScreen(component: RootBottomComponent, modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    text = "Bottom Bar nav with Decompose",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        )
    },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            /* do something */
                            component.openHome()
                        }) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openFeeds()
                        }) {
                            Icon(
                                Icons.Filled.Feed,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openMessage()
                        }) {
                            Icon(
                                Icons.Filled.NearMe,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openNotifications()
                        }) {
                            Icon(
                                Icons.Filled.Notifications,
                                contentDescription = "Localized description"
                            )
                        }

                    }
                }
            )

        },
        content = { innerpadding ->
            Column(modifier = Modifier.padding(innerpadding)) {
                Children(
                    stack = component.childStackBottom,
                    modifier = modifier,
                    animation = stackAnimation(fade() + scale()),
                ) {
                    when (val child = it.instance) {
                        is RootBottomComponent.ChildBottom.WelcomeChild -> WelcomeContent(component = child.component)
                        is RootBottomComponent.ChildBottom.FeedsChild -> FeedsContent(component = child.component)
                        is RootBottomComponent.ChildBottom.MessageChild -> MessageContent(component = child.component)
                        is RootBottomComponent.ChildBottom.NotificationsChild -> NotificationContent(
                            component = child.component
                        )
                    }
                }
            }
        })


}