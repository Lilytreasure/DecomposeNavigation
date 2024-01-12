package about

import platform.PlatformSpecific

interface AboutComponent {
    val loadFiles: PlatformSpecific

    fun onUpdateGreetingText()
    fun onBackClicked()


}