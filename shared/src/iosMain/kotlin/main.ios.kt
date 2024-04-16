import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import rootBottomStack.RootBottomComponent

actual fun getPlatformName(): String = "iOS"

fun MainViewController(component: RootBottomComponent, modifier: Modifier = Modifier) = ComposeUIViewController { App(component, modifier) }