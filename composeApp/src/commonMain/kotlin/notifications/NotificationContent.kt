package notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.toImageBitmap
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(component: NotificationComponent, modifier: Modifier = Modifier) {
    val mutableBitmapState: MutableState<ImageBitmap?> = mutableStateOf(null)
    var showCamera by rememberSaveable { mutableStateOf(false) }

    val state =
        rememberPeekabooCameraState(initialCameraMode = CameraMode.Back, onCapture = { bytes ->
            mutableBitmapState.value = bytes?.toImageBitmap()
            showCamera=false
        })

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Notify", fontSize = 15.sp) })
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                if (mutableBitmapState.value != null) {
                    mutableBitmapState.value?.let { image ->
                        Image(
                            bitmap = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(1.dp),
                            alignment = Alignment.CenterEnd
                        )
                    }

                }

//                ElevatedButton(modifier = Modifier
//                    .fillMaxWidth(), onClick = {
//                    component.platformSpecific.launchDialer("2547897567")
//
//                }) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            Icons.Default.Call,
//                            contentDescription = "Call Icon",
//                            tint = Color.Green,
//                            modifier = Modifier.padding(1.dp)
//                        )
//                        Text(text = "Call")
//                    }
//                }
                //FilesUpload
                //component.platformSpecific.CameraView()
                when {
                    showCamera -> {
                        Box(modifier = Modifier.absoluteOffset(x = 16.dp)) {
                            PeekabooCamera(
                                state = state,
                                modifier = Modifier.fillMaxSize(0.7f),
                                permissionDeniedContent = {
                                    PermissionDenied(
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                },
                            )
                            CameraOverlay(
                                isCapturing = state.isCapturing,
                                onBack = {
                                    showCamera = false

                                },
                                onCapture = {
                                    state.capture()
                                  //  showCamera = false
                                },
                                onConvert = { state.toggleCamera() },
                                modifier = Modifier.fillMaxSize(),
                            )
                        }


                    }

                    else -> {
                        Button(onClick = {
                            showCamera=true

                        }) {
                            Text(text = "Show Camera ")
                        }

                    }
                }

            }
        }
    }
}


@Composable
private fun CameraOverlay(
    isCapturing: Boolean,
    onCapture: () -> Unit,
    onConvert: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClick = onBack,
            modifier =
            Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Back Button",
                tint = Color.White,
            )
        }
        if (isCapturing) {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .size(80.dp)
                    .align(Alignment.Center),
                color = Color.White.copy(alpha = 0.7f),
                strokeWidth = 8.dp,
            )
        }
        CircularButton(
            imageVector = Icons.Outlined.Cached,
            modifier =
            Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            onClick = onConvert,
        )
        InstagramCameraButton(
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = onCapture,
        )
    }
}

@Composable
internal fun InstagramCameraButton(
    modifier: Modifier = Modifier,
    size: Dp = 70.dp,
    borderSize: Dp = 5.dp,
    onClick: () -> Unit,
) {
    // Outer size including the border
    val outerSize = size + borderSize * 2
    // Inner size excluding the border
    val innerSize = size - borderSize

    Box(
        modifier =
        modifier
            .size(outerSize)
            .clip(CircleShape)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        // Surface for the border effect
        Surface(
            modifier = Modifier.size(outerSize),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(borderSize, Color.White),
        ) {}

        // Inner clickable circle
        Box(
            modifier =
            Modifier
                .size(innerSize)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {}
    }
}

@Composable
fun CircularButton(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = Color.Black)
            .run {
                if (enabled) {
                    clickable { onClick() }
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun CircularButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    CircularButton(
        modifier = modifier,
        content = {
            Icon(imageVector, null, Modifier.size(34.dp), Color.White)
        },
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
fun PermissionDenied(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Warning Icon",
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Please grant the camera permission!",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}