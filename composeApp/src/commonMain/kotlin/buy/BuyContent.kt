package buy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import decomposeapp.composeapp.generated.resources.CoffeeCup
import decomposeapp.composeapp.generated.resources.Res
import decomposeapp.composeapp.generated.resources.framea
import decomposeapp.composeapp.generated.resources.frameb
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun FeedsContent(
    component: BuyComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Buy",
                fontSize = 15.sp
            )
        })
    }) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillParentMaxHeight(0.3f),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        ElevatedCardExample()
                    }
                    Text(
                        text = "Cappucino",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "with Chocolate",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row() {
                            Text(text = "4.8 (230)",
                                style = MaterialTheme.typography.titleSmall)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Image(
                                painter = painterResource(Res.drawable.framea),
                                contentDescription = "profile Picture",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(50.dp)
                            )
                            Image(
                                painter = painterResource(Res.drawable.frameb),
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
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "Size",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Text(
                                    text = "Price"
                                )
                                Text(
                                    text = "$4.53"
                                )
                            }
                            Column() {
                                Text(
                                    text = "Price"
                                )
                                Text(
                                    text = "$10.53"
                                )
                            }
                            Column() {
                                Text(
                                    text = "Price"
                                )
                                Text(
                                    text = "$20.53"
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FilledTonalButtonExample(
                                onClick = {},
                                label = " Buy Now "
                            )

                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(bottom = 100.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ElevatedCardExample() {
    ElevatedCard() {
        Column() {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(Res.drawable.CoffeeCup),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
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