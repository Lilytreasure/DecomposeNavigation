package about

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.PlatformSpecific

class DefaultAboutComponent(
    componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit,

) : AboutComponent, ComponentContext by componentContext, KoinComponent {
    override val loadFiles: PlatformSpecific by inject()
    override fun onUpdateGreetingText() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}