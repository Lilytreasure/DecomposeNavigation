package notifications

import platform.PlatformSpecific

interface NotificationComponent {
    val platformSpecific: PlatformSpecific
    fun onUpdateGreetingText()
    fun onBackClicked()


}