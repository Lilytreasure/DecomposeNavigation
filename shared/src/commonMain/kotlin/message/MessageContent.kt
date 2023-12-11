package message

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageContent(
    component: MessageComponent,
    modifier: Modifier = Modifier,
) {
    val mutableBitmapState: MutableState<ImageBitmap?> = mutableStateOf(null)
    val mutableFrontIdBitmapState: MutableState<ImageBitmap?> = mutableStateOf(null)
    val mutableBackIdBitmapState: MutableState<ImageBitmap?> = mutableStateOf(null)
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val color = MaterialTheme.colorScheme.onBackground
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Small Top App Bar")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            item {
                Spacer(modifier = Modifier.padding(innerPadding))
            }
            item {
                Text(
                    text = "Please upload a copy of the identity card and  clear passport/ID photo",
                    fontSize = 14.sp
                )
                Text(
                    text = "Upload these documents",
                    fontSize = 12.sp
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .wrapContentHeight()
                        .clickable {
                            component.loadFiles.loadImages { image ->
                                println("Loaded camera image + " + image)
                                mutableBitmapState.value = image
                            }
                        }
                        .drawBehind {
                            drawRoundRect(color = color, style = stroke)
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                text = "Take Passport Photo",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Please not  that Screenshots, Mobile phone bills and insurance are not accepted for verification",
                                fontSize = 10.sp,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        if (mutableBitmapState.value == null) {
                            Image(
                                painter = painterResource("camera.png"),
                                contentDescription = "Take photo",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(end = 10.dp)
                                    .clickable {
                                        component.loadFiles.loadImages { image ->
                                            println("Loaded camera image + " + image)
                                            mutableBitmapState.value = image
                                        }
                                    }
                            )
                        } else {
                            mutableBitmapState.value?.let { image ->
                                Image(
                                    bitmap = image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(1.dp),
                                    alignment = Alignment.CenterEnd
                                )
                            }
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .wrapContentHeight()
                        .clickable {
                            component.loadFiles.loadFiles { image ->
                                println("Loaded camera image + " + image)
                                mutableFrontIdBitmapState.value = image
                            }
                        }
                        .drawBehind {
                            drawRoundRect(color = color, style = stroke)
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                text = "Front Side of your",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "National ID/Passport",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Upload front side",
                                fontSize = 10.sp
                            )
                            Text(
                                text = "A front  picture of your National ID/Passport",
                                fontSize = 10.sp,
                                style =MaterialTheme.typography.bodySmall
                            )

                        }
                        if (mutableFrontIdBitmapState.value == null) {
                            Image(
                                painter = painterResource("id-card.png"),
                                contentDescription = "Take photo",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(70.dp)
                                    .clickable {
                                        component.loadFiles.loadFiles { image ->
                                            println("Loaded camera image + " + image)
                                            mutableFrontIdBitmapState.value = image
                                        }
                                    }
                            )
                        } else {
                            mutableFrontIdBitmapState.value.let { image ->
                                if (image != null) {
                                    Image(
                                        bitmap = image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(1.dp),
                                        alignment = Alignment.CenterEnd
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .wrapContentHeight()
                        .clickable {
                            component.loadFiles.loadFiles { image ->
                                println("Loaded camera image + " + image)
                                mutableBackIdBitmapState.value = image
                            }
                        }
                        .drawBehind {
                            drawRoundRect(color = color, style = stroke)
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                text = "Back Side of your",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "National ID/Passport",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Upload back side",
                                fontSize = 10.sp
                            )
                            Text(
                                text = "A back picture of your National ID/Passport",
                                fontSize = 10.sp,
                                style =MaterialTheme.typography.bodySmall
                            )

                        }
                        if (mutableBackIdBitmapState.value == null) {
                            Image(
                                painter = painterResource("id-card.png"),
                                contentDescription = "Take photo",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .size(70.dp)
                                    .clickable {
                                        component.loadFiles.loadFiles { image ->
                                            println("Loaded camera image + " + image)
                                            mutableBackIdBitmapState.value = image
                                        }
                                    }
                            )
                        } else {
                            mutableBackIdBitmapState.value.let { image ->
                                if (image != null) {
                                    Image(
                                        bitmap = image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(1.dp),
                                        alignment = Alignment.CenterEnd
                                    )
                                }

                            }

                        }
                    }
                }
            }
            item {
                Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {

                }
            }

        }
    }
}


