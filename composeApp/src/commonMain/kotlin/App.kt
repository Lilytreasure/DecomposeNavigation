import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import rootBottomStack.RootBottomComponent
import rootBottomStack.RootBottomScreen
import theme.ComposeExperimentalTheme

@Composable
@Preview
fun App(component: RootBottomComponent, modifier: Modifier = Modifier) {
    ComposeExperimentalTheme(content = {
        Scaffold() { paddingFromPrent ->
            Column(
                Modifier
                    .padding(paddingFromPrent)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                RootBottomScreen(component, modifier)

            }
        }
    })
}