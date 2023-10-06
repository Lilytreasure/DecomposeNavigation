package listing

import com.arkivanov.decompose.ComponentContext

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit,
) : MainComponent, ComponentContext by componentContext {


    override fun onUpdateGreetingText() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}