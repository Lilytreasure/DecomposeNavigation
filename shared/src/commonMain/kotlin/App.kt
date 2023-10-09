import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import rootBottomStack.RootBottomComponent
import rootBottomStack.RootBottomScreen

@Composable
fun App(component: RootBottomComponent, modifier: Modifier = Modifier) {
    //To  be modified
    MaterialTheme {
        Scaffold() { paddingFromPrent ->

            //Todo---adding  the parent recycler view

            Column(
                Modifier
                    .padding(paddingFromPrent)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RootBottomScreen(component, modifier)

            }
        }
    }
}

expect fun getPlatformName(): String