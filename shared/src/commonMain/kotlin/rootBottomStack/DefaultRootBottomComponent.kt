package rootBottomStack

import about.AboutComponent
import about.DefaultAboutComponent
import buy.BuyComponent
import buy.DefautBuyComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import home.DefaultHomeComponent
import home.HomeComponent
import notifications.DefaultNotificationComponent
import notifications.NotificationComponent


class DefaultRootBottomComponent(
    componentContext: ComponentContext

) : RootBottomComponent, ComponentContext by componentContext {
    private val navigationBottomStackNavigation = StackNavigation<ConfigBottom>()

    private val _childStackBottom =
        childStack(
            source = navigationBottomStackNavigation,
            initialConfiguration = ConfigBottom.Welcome,
            handleBackButton = true,
            childFactory = ::createChildBottom,
            key = "authStack"
        )

    override val childStackBottom: Value<ChildStack<*, RootBottomComponent.ChildBottom>> =
        _childStackBottom


    private fun createChildBottom(
        config: ConfigBottom,
        componentContext: ComponentContext
    ): RootBottomComponent.ChildBottom =
        when (config) {

            is ConfigBottom.Welcome -> RootBottomComponent.ChildBottom.WelcomeChild(
                welcomeComponent(componentContext)
            )

            is ConfigBottom.Feeds -> RootBottomComponent.ChildBottom.FeedsChild(
                feedsComponent(componentContext)
            )

            is ConfigBottom.Message -> RootBottomComponent.ChildBottom.MessageChild(
                messageComponent(componentContext)
            )

            is ConfigBottom.Notification -> RootBottomComponent.ChildBottom.NotificationsChild(
                notificationComponent(componentContext)
            )
        }


    private fun welcomeComponent(componentContext: ComponentContext): HomeComponent =
        DefaultHomeComponent(
            componentContext = componentContext,
            onFinished = {

            }

        )

    private fun feedsComponent(componentContext: ComponentContext): BuyComponent =
        DefautBuyComponent(
            componentContext = componentContext,
            onShowWelcome = {

            }

        )

    private fun messageComponent(componentContext: ComponentContext): AboutComponent =
        DefaultAboutComponent(
            componentContext = componentContext,
            onShowWelcome = {

            }

        )

    private fun notificationComponent(componentContext: ComponentContext): NotificationComponent =
        DefaultNotificationComponent(
            componentContext = componentContext,
            onShowWelcome = {

            }

        )

    override fun openHome() {
        navigationBottomStackNavigation.bringToFront(ConfigBottom.Welcome)
    }

    override fun openFeeds() {
        navigationBottomStackNavigation.bringToFront(ConfigBottom.Feeds)
    }

    override fun openMessage() {
        navigationBottomStackNavigation.bringToFront(ConfigBottom.Message)
    }

    override fun openNotifications() {
        navigationBottomStackNavigation.bringToFront(ConfigBottom.Notification)
    }


    private sealed class ConfigBottom : Parcelable {
        @Parcelize
        data object Welcome : ConfigBottom()

        @Parcelize
        data object Feeds : ConfigBottom()

        @Parcelize
        data object Message : ConfigBottom()

        @Parcelize
        data object Notification : ConfigBottom()

    }

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onResume() {
                when (childStackBottom.active.configuration) {
                    is ConfigBottom.Message -> {
                        super.onResume()
                    }

                }
            }
        })

    }

}