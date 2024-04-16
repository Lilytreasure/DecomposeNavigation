package about

import PlatformSpecific


interface AboutComponent {
    val loadFiles: PlatformSpecific

    fun onUpdateGreetingText()
    fun onBackClicked()
}