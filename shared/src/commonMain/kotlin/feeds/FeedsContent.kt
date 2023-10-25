package feeds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {

                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedCardExample()
                }
                Text(
                    text = "Cappucino",
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(text = "with Chocolate")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row() {
                        Text(text = "4.8 (230)")
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Image(
                            painter = painterResource("Frame 19.png"),
                            contentDescription = "profile Picture",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(50.dp)
                        )
                        Image(
                            painter = painterResource("Frame 20.png"),
                            contentDescription = "profile Picture",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                Text(
                    text = "Description",
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "A cappuccino is an approximately 150 ml (5 oz) beverage, with 25 ml of espresso coffee and 85ml of fresh milk the fo.. Read More",
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "Size",
                    modifier = Modifier.padding(top = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButtonExample(
                        onClick = {},
                        label = "S"
                    )
                    FilledTonalButtonExample(
                        onClick = {},
                        label = "M"
                    )
                    FilledTonalButtonExample(
                        onClick = {},
                        label = "L"
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column() {
                        Text(
                            text = "Price"
                        )
                        Text(
                            text = "$4.53"
                        )
                    }
                    FilledTonalButtonExample(
                        onClick = {},
                        label = " Buy Now "
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ElevatedCardExample() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
    ) {
        Image(
            painter = painterResource("CoffeeCup.png"),
            contentDescription = "profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FilledTonalButtonExample(
    onClick: () -> Unit,
    label: String
) {
    FilledTonalButton(onClick = { onClick() }) {
        Text(text = label)
    }
}