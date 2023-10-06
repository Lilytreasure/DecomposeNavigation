package root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import listing2.DefaultWelcomeComponent
import listing2.WelcomeComponent


class DefaultRootComponent(
    componentContext: ComponentContext,
    val storeFactory: StoreFactory,
    val mainContext: CoroutineDispatcher,
) : RootComponent, ComponentContext by componentContext{

    private val navigationBottomStackNavigation = StackNavigation<ConfigBottom>()

    private val _childStackBottom =
        childStack(
            source = navigationBottomStackNavigation,
            initialConfiguration = ConfigBottom.Settings,
            handleBackButton = true,
            childFactory = ::createChildBottom,
            key = "authStack"
        )

    override val childStackBottom: Value<ChildStack<*, RootComponent.ChildBottom>> =
        _childStackBottom

    private fun createChildBottom(
        config: ConfigBottom,
        componentContext: ComponentContext
    ): RootComponent.ChildBottom =
        when (config) {

            is ConfigBottom.Settings -> RootComponent.ChildBottom.SettingsChild(
                settingsComponent(componentContext)
            )
        }



    private fun settingsComponent(componentContext: ComponentContext): WelcomeComponent =
        DefaultWelcomeComponent(
            componentContext = componentContext,
            onFinished = {

            }

        )


    private sealed class ConfigBottom : Parcelable {

        @Parcelize
        data object Settings : ConfigBottom()

    }


}