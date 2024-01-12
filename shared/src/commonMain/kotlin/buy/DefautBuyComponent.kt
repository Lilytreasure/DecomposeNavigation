package buy

import com.arkivanov.decompose.ComponentContext

class DefautBuyComponent(
    componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit,
) : BuyComponent, ComponentContext by componentContext {


    override fun onUpdateGreetingText() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}