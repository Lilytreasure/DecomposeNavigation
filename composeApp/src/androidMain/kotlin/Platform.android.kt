import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import rootBottomStack.RootBottomComponent

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

@Composable
fun MainView(component: RootBottomComponent, modifier: Modifier = Modifier) = App(component, modifier)

actual fun getPlatform(): Platform = AndroidPlatform()