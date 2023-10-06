package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import listing.DefaultMainComponent
import listing2.DefaultWelcomeComponent
import listing.MainComponent
import listing2.WelcomeComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val navigationBottomStackNavigation = StackNavigation<Config>()

    private val _childStackBottom =
        childStack(
            source = navigationBottomStackNavigation,
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::child,
            key = "authStack"
        )

    override val stack: Value<ChildStack<*, RootComponent.Child>> = _childStackBottom
    //Create a  default Constructor

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(
                mainComponent(componentContext)
            )

            is Config.Welcome -> RootComponent.Child.Welcome(
                welcomeComponent(componentContext)
            )
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            onShowWelcome = {
                //  navigation.push(Config.Welcome)
            },
        )

    private fun welcomeComponent(componentContext: ComponentContext): WelcomeComponent =
        DefaultWelcomeComponent(
            componentContext = componentContext,
            onFinished = navigation::pop,
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private sealed class Config : Parcelable {
        @Parcelize
        data object Main : Config()

        @Parcelize
        data object Welcome : Config()

    }
}
