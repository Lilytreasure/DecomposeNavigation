import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.atwa.filepicker.core.FilePicker
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.absoluteValue

actual open class PlatformSpecific(private val context: Context) : AppCompatActivity() {
    private val PICK_FILE_REQUEST_CODE = 123
    private val currentActivity: AppCompatActivity = (context as AppCompatActivity)
    private val filePicker = FilePicker.getInstance(currentActivity)
    private val PICK_IMAGE_REQUEST_CODE = 123
    private val CAMERA_PERMISSION_REQUEST_CODE = 123
    val filePickerLauncher =
        currentActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleFileSelectionResult(result.resultCode, result.data?.data)
        }
    private val executor = Executors.newSingleThreadExecutor()

    //Todo api modifications
    //Modified to support android version below and above 13
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    actual fun loadFiles(callback: (ImageBitmap?) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                currentActivity,
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                } else {
                    Manifest.permission.READ_MEDIA_IMAGES
                }
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadDeviceFiles(callback)
        } else {
            ActivityCompat.requestPermissions(
                currentActivity,
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                },
                PICK_FILE_REQUEST_CODE
            )
        }
    }

    actual fun loadImages(callback: (ImageBitmap?) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                currentActivity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, proceed with capturing the image
            captureImage(callback)
        } else {
            // Request camera permission
            ActivityCompat.requestPermissions(
                currentActivity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }
    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*" // Set the MIME type to allow only image files

        currentActivity.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Handle the selected image URI
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let { uri ->
                // Convert URI to Bitmap
                val bitmap: Bitmap? = uriToBitmap(uri)

                if (bitmap != null) {
                    // Do something with the bitmap

                }
            }
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = currentActivity.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun captureImage(callback: (ImageBitmap?) -> Unit) {
        filePicker.captureCameraImage { meta ->
            val name: String? = meta?.name
            val sizeKb: Int? = meta?.sizeKb
            val file: File? = meta?.file
            val bitmap: Bitmap? = meta?.bitmap
            val bitmapimage: ImageBitmap? = meta?.bitmap?.asImageBitmap()

            println("Picked Image:::$bitmap")

            // Invoke the callback with the captured image details
            callback(bitmapimage)
        }
    }

    private fun loadDeviceFiles(callback: (ImageBitmap?) -> Unit) {
        filePicker.pickImage() { meta ->
            val name: String? = meta?.name
            val sizeKb: Int? = meta?.sizeKb
            val file: File? = meta?.file
            val bitmapimage: ImageBitmap? = meta?.bitmap?.asImageBitmap()
            callback(bitmapimage)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun openFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        filePickerLauncher.launch(intent)
    }

    private fun pickPdf() {
        filePicker.pickPdf() { meta ->
            val name: String? = meta?.name
            val sizeKb: Int? = meta?.sizeKb
            val file: File? = meta?.file
            println("Loaded File name:::::" + name)
        }
    }

    private fun getFileName(uri: Uri): String {
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            return cursor.getString(nameIndex)
        }
        return ""
    }

    private fun handleFileSelectionResult(resultCode: Int, selectedFileUri: Uri?) {
        if (resultCode == Activity.RESULT_OK) {
            selectedFileUri?.let { uri ->
                // Get the file name from the URI
                val fileName = getFileName(uri)

                // Print or use the file name as needed
                println("Selected file name: $fileName")
            }
        } else {
            println("Error opening or user canceled")
        }
    }

    actual fun launchDialer(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

    }

    @Composable
    actual fun UploadFiles() {
        Column(modifier =Modifier.fillMaxSize()){
            Text(text = "UPload docs")

        }

    }

    //Todo -----Under Heavy development
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    actual fun CameraView() {
        val cameraPermissionState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
        if (cameraPermissionState.allPermissionsGranted) {
            CameraWithGrantedPermission()
        } else {
            LaunchedEffect(Unit) {
                cameraPermissionState.launchMultiplePermissionRequest()
            }
        }

    }
    @Composable
    private fun CameraWithGrantedPermission(
//        modifier: Modifier,
//        onCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit
    ) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val viewScope = rememberCoroutineScope()
        var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

        val preview = Preview.Builder().build()
        val previewView = remember { PreviewView(context) }
        val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
        var isFrontCamera by rememberSaveable { mutableStateOf(false) }
        val cameraSelector = remember(isFrontCamera) {
            val lensFacing =
                if (isFrontCamera) {
                    CameraSelector.LENS_FACING_FRONT
                } else {
                    CameraSelector.LENS_FACING_BACK
                }
            CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()
        }

        DisposableEffect(Unit) {
            onDispose {
                cameraProvider?.unbindAll()
            }
        }

//        LaunchedEffect(isFrontCamera) {
//            cameraProvider = suspendCoroutine<ProcessCameraProvider> { continuation ->
//                ProcessCameraProvider.getInstance(context).also { cameraProvider ->
//                    cameraProvider.addListener({
//                        continuation.resume(cameraProvider.get())
//                    }, executor)
//                }
//            }
//            cameraProvider?.unbindAll()
//            cameraProvider?.bindToLifecycle(
//                lifecycleOwner,
//                cameraSelector,
//                preview,
//                imageCapture
//            )
//            preview.setSurfaceProvider(previewView.surfaceProvider)
//        }
        //val nameAndDescription = createNewPhotoNameAndDescription()
        var capturePhotoStarted by remember { mutableStateOf(false) }

        Box(modifier = Modifier.pointerInput(isFrontCamera) {
            detectHorizontalDragGestures { change, dragAmount ->
                if (dragAmount.absoluteValue > 50.0) {
                    isFrontCamera = !isFrontCamera
                }
            }
        }) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
//            CircularButton(
//                imageVector = IconPhotoCamera,
//                modifier = Modifier.align(Alignment.BottomCenter).padding(36.dp),
//                enabled = !capturePhotoStarted,
//            ) {
//                fun addLocationInfoAndReturnResult(imageBitmap: ImageBitmap) {
//                    fun sendToStorage(gpsPosition: GpsPosition) {
//                        onCapture(
//                            createCameraPictureData(
//                                name = nameAndDescription.name,
//                                description = nameAndDescription.description,
//                                gps = gpsPosition
//                            ),
//                            AndroidStorableImage(imageBitmap)
//                        )
//                        capturePhotoStarted = false
//                    }
//                    LocationServices.getFusedLocationProviderClient(context)
//                        .getCurrentLocation(CurrentLocationRequest.Builder().build(), null)
//                        .apply {
//                            addOnSuccessListener {
//                                sendToStorage(GpsPosition(it.latitude, it.longitude))
//                            }
//                            addOnFailureListener {
//                                sendToStorage(GpsPosition(0.0, 0.0))
//                            }
//                        }
//                }
//
//                capturePhotoStarted = true
//                imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
//                    override fun onCaptureSuccess(image: ImageProxy) {
//                        val byteArray: ByteArray = image.planes[0].buffer.toByteArray()
//                        val imageBitmap = byteArray.toImageBitmap()
//                        image.close()
//                        addLocationInfoAndReturnResult(imageBitmap)
//                    }
//                })
//                viewScope.launch {
//                    // TODO: There is a known issue with Android emulator
//                    //  https://partnerissuetracker.corp.google.com/issues/161034252
//                    //  After 5 seconds delay, let's assume that the bug appears and publish a prepared photo
//                    delay(5000)
//                    if (capturePhotoStarted) {
//                        addLocationInfoAndReturnResult(
//                            Res.readBytes("files/android-emulator-photo.jpg").toImageBitmap()
//                        )
//                    }
//                }
//            }
            if (capturePhotoStarted) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp).align(Alignment.Center),
                    color = Color.White.copy(alpha = 0.7f),
                    strokeWidth = 8.dp,
                )
            }
        }
    }
}