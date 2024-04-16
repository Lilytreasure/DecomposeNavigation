import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSafeArea
import androidx.compose.ui.platform.PlatformInsets
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.moriatsushi.insetsx.WindowInsetsUIViewController
import di.initKoin
import platform.UIKit.UIViewController
import rootBottomStack.DefaultRootBottomComponent
import theme.ComposeExperimentalTheme

@OptIn(ExperimentalComposeUiApi::class, InternalComposeApi::class)
@Suppress("unused", "FunctionName")
fun MainViewController(
    lifecycle: LifecycleRegistry,
    topSafeArea: Float,
    bottomSafeArea: Float,
): UIViewController {
    val defaultComponentCtx = DefaultComponentContext(lifecycle = lifecycle)
    val root = DefaultRootBottomComponent(
        componentContext = defaultComponentCtx
    )
    initKoin(enableNetworkLogs = true, platform = PlatformSpecific())
    return WindowInsetsUIViewController {
        val density = LocalDensity.current
        val topSafeAreaDp = with(density) { topSafeArea.toDp() }
        val bottomSafeAreaDp = with(density) { bottomSafeArea.toDp() }
        val safeArea = PlatformInsets(top = topSafeAreaDp + 10.dp, bottom = bottomSafeAreaDp)
        CompositionLocalProvider(LocalSafeArea provides safeArea) {
            ComposeExperimentalTheme {
                App(root)
            }
        }
    }
}