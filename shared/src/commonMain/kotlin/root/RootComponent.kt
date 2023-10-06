package root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import listing2.WelcomeComponent


interface RootComponent {
    val childStackBottom: Value<ChildStack<*, ChildBottom>>

    sealed class ChildBottom {
        class SettingsChild(val component: WelcomeComponent) : ChildBottom()
    }


}