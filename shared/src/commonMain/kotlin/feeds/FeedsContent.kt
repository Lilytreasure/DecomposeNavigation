package feeds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun FeedsContent(
    component: FeedsComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        /* do something */
                    }) {
                        Icon(
                            Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Localized description",
                        )
                    }
                    Text(
                        "Detail",
                        modifier = Modifier
                    )
                    IconButton(onClick = {
                        /* do something */
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                        )
                    }
                }

            }
        )
    }) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource("img.png"),
                contentDescription = "profile Picture",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.4f)
            )
        }

    }
}