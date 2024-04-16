import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rootBottomStack.RootBottomComponent

actual fun getPlatformName(): String = "Android"

@Composable fun MainView(component: RootBottomComponent, modifier: Modifier = Modifier) = App(component, modifier)
