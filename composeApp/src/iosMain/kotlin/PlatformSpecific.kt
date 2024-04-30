import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSURL
import platform.Photos.PHPhotoLibrary
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationAssetRepresentationModeCurrent
import platform.PhotosUI.PHPickerConfigurationSelectionOrdered
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraDevice
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UITextField
import platform.UniformTypeIdentifiers.UTTypeImage
import platform.darwin.NSInteger
import platform.darwin.NSObject
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.AVFoundation.*
import platform.AVFoundation.AVCaptureDeviceDiscoverySession.Companion.discoverySessionWithDeviceTypes
import platform.AVFoundation.AVCaptureDeviceInput.Companion.deviceInputWithDevice
import platform.CoreGraphics.CGRect
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIView


private sealed interface CameraAccess {
    object Undefined : CameraAccess
    object Denied : CameraAccess
    object Authorized : CameraAccess
}

private val deviceTypes = listOf(
    AVCaptureDeviceTypeBuiltInWideAngleCamera,
    AVCaptureDeviceTypeBuiltInDualWideCamera,
    AVCaptureDeviceTypeBuiltInDualCamera,
    AVCaptureDeviceTypeBuiltInUltraWideCamera,
    AVCaptureDeviceTypeBuiltInDuoCamera
)

data class Name(
    val name: String = "",
    val description: String = ""
)


actual open class PlatformSpecific {
    actual fun loadFiles(callback: (ImageBitmap?) -> Unit) {
        //Todo ----
        //convert  an image to a Imagebitmap equivalent in swift
        fun createPHPickerViewController(
            delegate: PHPickerViewControllerDelegateProtocol,
            selectionMode: NSInteger,
        ): PHPickerViewController {
            val configuration = PHPickerConfiguration(PHPhotoLibrary.sharedPhotoLibrary())
            val newFilter =
                PHPickerFilter.anyFilterMatchingSubfilters(
                    listOf(
                        PHPickerFilter.imagesFilter(),
                    ),
                )
            configuration.filter = newFilter
            configuration.preferredAssetRepresentationMode =
                PHPickerConfigurationAssetRepresentationModeCurrent
            configuration.selection = PHPickerConfigurationSelectionOrdered
            configuration.selectionLimit = selectionMode
            configuration.preselectedAssetIdentifiers = listOf<Nothing>()
            val picker = PHPickerViewController(configuration)
            picker.delegate = delegate
            return picker
        }

        val pickerDelegateImage = object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(
                picker: PHPickerViewController,
                didFinishPicking: List<*>,
            ) {
                picker.dismissViewControllerAnimated(true, null)
                println("didFinishPickingSelectedImage: $didFinishPicking")
                didFinishPicking.forEach {
                    val result = it as? PHPickerResult ?: return@forEach
                    result.itemProvider.loadFileRepresentationForTypeIdentifier(
                        typeIdentifier = UTTypeImage.identifier,
                    ) { url, error ->
                        if (error != null) {
                            println("Error: $error")
                            return@loadFileRepresentationForTypeIdentifier
                        }
                        // onResult(listOfNotNull(url?.let(::KmpFile)))
                    }
                }
            }
        }
        val imagePicker =
            createPHPickerViewController(
                delegate = pickerDelegateImage,
                selectionMode = 1,
            )
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            imagePicker,
            true,
            null,
        )
    }

    actual fun loadImages(callback: (ImageBitmap?) -> Unit) {
        val cameraVc = UIImagePickerController()
        val cameraVcs =
            UIImagePickerController.isCameraDeviceAvailable(cameraDevice = UIImagePickerControllerCameraDevice.UIImagePickerControllerCameraDeviceRear)
        platform.AVFoundation.AVCaptureDevice.requestAccessForMediaType(mediaType = AVMediaTypeVideo) {
            if (it) {
                println("Access granted;;;;")
                if (cameraVcs) {
                    cameraVc.sourceType =
                        UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
                    // cameraVc.cameraDevice()
                    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                        cameraVc,
                        true,
                        null,
                    )
                    println("Camera found")
                } else {
                    println("Camera not found")
                }

            } else {

                println("Acces denied;;;;;;;")

            }
        }

    }

    actual fun launchDialer(phoneNumber: String) {
        val urlString = "tel://$phoneNumber"
        val url = NSURL(string = urlString)
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }

    }

    @OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
    @Composable
    actual fun UploadFiles() {
        var loadedfiles = ""

        fun createPHPickerViewController(
            delegate: PHPickerViewControllerDelegateProtocol,
            selectionMode: NSInteger,
        ): PHPickerViewController {
            val configuration = PHPickerConfiguration(PHPhotoLibrary.sharedPhotoLibrary())
            val newFilter =
                PHPickerFilter.anyFilterMatchingSubfilters(
                    listOf(
                        PHPickerFilter.imagesFilter(),
                    ),
                )
            configuration.filter = newFilter
            configuration.preferredAssetRepresentationMode =
                PHPickerConfigurationAssetRepresentationModeCurrent
            configuration.selection = PHPickerConfigurationSelectionOrdered
            configuration.selectionLimit = selectionMode
            configuration.preselectedAssetIdentifiers = listOf<Nothing>()
            val picker = PHPickerViewController(configuration)
            picker.delegate = delegate
            return picker
        }

        val pickerDelegateImage = object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(
                picker: PHPickerViewController,
                didFinishPicking: List<*>,
            ) {
                picker.dismissViewControllerAnimated(true, null)
                println("didFinishPickingSelectedImage: $didFinishPicking")
                didFinishPicking.forEach {
                    val result = it as? PHPickerResult ?: return@forEach
                    result.itemProvider.loadFileRepresentationForTypeIdentifier(
                        typeIdentifier = UTTypeImage.identifier,
                    ) { url, error ->
                        if (error != null) {
                            println("Error: $error")
                            return@loadFileRepresentationForTypeIdentifier
                        }

                        if (url != null) {
                            loadedfiles = url.toString()


                        }

                        // onResult(listOfNotNull(url?.let(::KmpFile)))
                    }
                }
            }
        }
        val imagePicker =
            createPHPickerViewController(
                delegate = pickerDelegateImage,
                selectionMode = 1,
            )
        Column() {
            Text("file Upload view ")
            Button(modifier = Modifier.padding(vertical = 16.dp),
                onClick = {
                    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                        imagePicker,
                        true,
                        null,
                    )

                }, content = {
                    Text("Load Files")

                })
            Column() {
                var message by remember { mutableStateOf("Hello, World!") }
                var message1 by remember { mutableStateOf("Text 1") }
                var message2 by remember { mutableStateOf("Text 2") }

                UIKitView(
                    factory = {
//                        val textView1 = UITextView().apply {
//                            translatesAutoresizingMaskIntoConstraints = false
//                            text = message1
//                            backgroundColor = UIColor.blueColor
//                       }
//                        val textView2 = UITextView().apply {
//                            translatesAutoresizingMaskIntoConstraints = false
//                            text = message2
//                            backgroundColor = UIColor.blueColor
//                        }
//                        imagesViewController.view.addSubview(textView1)
//                        imagesViewController.view.addSubview(textView2)

                        val textField = object : UITextField(CGRectMake(0.0, 0.0, 0.0, 0.0)) {
                            @ObjCAction
                            fun editingChanged() {
                                message = text ?: ""
                            }
                        }
                        textField.addTarget(
                            target = textField,
                            action = NSSelectorFromString(textField::editingChanged.name),
                            forControlEvents = UIControlEventEditingChanged
                        )

                        //modified
//                        val imageName = "yourImage.png"
//                        var image = UIImage( imageName)
//                        var imageView = UIImageView(image)
//                        imageView.translatesAutoresizingMaskIntoConstraints = false
//                        imageView.sizeThatFits(CGSizeMake( 100.0, 100.0))

                        //Create  a parent view to  add the images
//                        val containerView = UIView().apply {
//                            translatesAutoresizingMaskIntoConstraints = false
//                            backgroundColor = UIColor.brownColor
//                        }
//                        val textView1 = UITextView().apply {
//                            translatesAutoresizingMaskIntoConstraints = false
//                            text = message1
//                            backgroundColor = UIColor.blueColor
//                        }
//                        val textView2 = UITextView().apply {
//                            translatesAutoresizingMaskIntoConstraints = false
//                            text = message2
//                            backgroundColor = UIColor.lightGrayColor
//                        }
//                        containerView.addSubview(textView1)
//                        containerView.addSubview(textView2)


                        textField
                    },
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    update = { textField ->
                        textField.text = message
                    },
                    interactive = true
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                UIKitView(
                    factory = {

                        val textField = object : UITextField(CGRectMake(0.0, 0.0, 0.0, 0.0)) {
                            @ObjCAction
                            fun editingChanged() {
                                message = text ?: ""
                            }
                        }
                        textField.addTarget(
                            target = textField,
                            action = NSSelectorFromString(textField::editingChanged.name),
                            forControlEvents = UIControlEventEditingChanged
                        )
                        textField
                    },
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    update = { textField ->
                        textField.text = message
                    },
                    interactive = true
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))

                //Use swift ui

            }
        }
    }



    //Todo -----Under Heavy development

    @Composable
    actual fun CameraView(

    ) {
        val defaultOnCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit =
            { picture, image ->
                // Your default implementation here
                // For example:
                println("Picture captured with default implementation.")
                println("Picture Data: $picture")
                println("Platform Storable Image: $image")

            }

        var cameraAccess: CameraAccess by remember { mutableStateOf(CameraAccess.Undefined) }
        LaunchedEffect(Unit) {
            when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
                AVAuthorizationStatusAuthorized -> {
                    cameraAccess = CameraAccess.Authorized
                }

                AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                    cameraAccess = CameraAccess.Denied
                }

                AVAuthorizationStatusNotDetermined -> {
                    AVCaptureDevice.requestAccessForMediaType(
                        mediaType = AVMediaTypeVideo
                    ) { success ->
                        cameraAccess = if (success) CameraAccess.Authorized else CameraAccess.Denied
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            when (cameraAccess) {
                CameraAccess.Undefined -> {
                    // Waiting for the user to accept permission
                }

                CameraAccess.Denied -> {
                    Text("Camera access denied", color = Color.White)
                }

                CameraAccess.Authorized -> {
                    AuthorizedCamera(defaultOnCapture)
                }
            }
        }
    }

    @Composable
    private fun BoxScope.AuthorizedCamera(
        onCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit
    ) {
        val camera: AVCaptureDevice? = remember {
            discoverySessionWithDeviceTypes(
                deviceTypes = deviceTypes,
                mediaType = AVMediaTypeVideo,
                position = AVCaptureDevicePositionFront,
            ).devices.firstOrNull() as? AVCaptureDevice
        }
        if (camera != null) {
            RealDeviceCamera(camera, onCapture)
        } else {
            Text(
                """
            Camera is not available on simulator.
            Please try to run on a real iOS device.
        """.trimIndent(), color = Color.White
            )
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    private fun BoxScope.RealDeviceCamera(
        camera: AVCaptureDevice,
        onCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit
    ) {
        val capturePhotoOutput = remember { AVCapturePhotoOutput() }
        var actualOrientation by remember {
            mutableStateOf(
                AVCaptureVideoOrientationPortrait
            )
        }
        val locationManager = remember {
            CLLocationManager().apply {
                desiredAccuracy = kCLLocationAccuracyBest
                requestWhenInUseAuthorization()
            }
        }
        val nameAndDescription = Name()
        var capturePhotoStarted by remember { mutableStateOf(false) }
        val photoCaptureDelegate = remember {
            object : NSObject(), AVCapturePhotoCaptureDelegateProtocol {
                override fun captureOutput(
                    output: AVCapturePhotoOutput,
                    didFinishProcessingPhoto: AVCapturePhoto,
                    error: NSError?
                ) {
                    val photoData = didFinishProcessingPhoto.fileDataRepresentation()
                    if (photoData != null) {
                        val gps = locationManager.location?.toGps() ?: GpsPosition(0.0, 0.0)
                        val uiImage = UIImage(photoData)
                        onCapture(
                            createCameraPictureData(
                                name = nameAndDescription.name,
                                description = nameAndDescription.description,
                                gps = gps
                            ),
                            IosStorableImage(uiImage)
                        )
                    }
                    capturePhotoStarted = false
                }
            }
        }

        val captureSession: AVCaptureSession = remember {
            AVCaptureSession().also { captureSession ->
                captureSession.sessionPreset = AVCaptureSessionPresetPhoto
                val captureDeviceInput: AVCaptureDeviceInput =
                    deviceInputWithDevice(device = camera, error = null)!!
                captureSession.addInput(captureDeviceInput)
                captureSession.addOutput(capturePhotoOutput)
            }
        }
        val cameraPreviewLayer = remember {
            AVCaptureVideoPreviewLayer(session = captureSession)
        }

        DisposableEffect(Unit) {
            class OrientationListener : NSObject() {
                @Suppress("UNUSED_PARAMETER")
                @ObjCAction
                fun orientationDidChange(arg: NSNotification) {
                    val cameraConnection = cameraPreviewLayer.connection
                    if (cameraConnection != null) {
                        actualOrientation = when (UIDevice.currentDevice.orientation) {
                            UIDeviceOrientation.UIDeviceOrientationPortrait ->
                                AVCaptureVideoOrientationPortrait

                            UIDeviceOrientation.UIDeviceOrientationLandscapeLeft ->
                                AVCaptureVideoOrientationLandscapeRight

                            UIDeviceOrientation.UIDeviceOrientationLandscapeRight ->
                                AVCaptureVideoOrientationLandscapeLeft

                            UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown ->
                                AVCaptureVideoOrientationPortrait

                            else -> cameraConnection.videoOrientation
                        }
                        cameraConnection.videoOrientation = actualOrientation
                    }
                    capturePhotoOutput.connectionWithMediaType(AVMediaTypeVideo)
                        ?.videoOrientation = actualOrientation
                }
            }

            val listener = OrientationListener()
            val notificationName = platform.UIKit.UIDeviceOrientationDidChangeNotification
            NSNotificationCenter.defaultCenter.addObserver(
                observer = listener,
                selector = NSSelectorFromString(
                    OrientationListener::orientationDidChange.name + ":"
                ),
                name = notificationName,
                `object` = null
            )
            onDispose {
                NSNotificationCenter.defaultCenter.removeObserver(
                    observer = listener,
                    name = notificationName,
                    `object` = null
                )
            }
        }
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            background = Color.Black,
            factory = {
                val cameraContainer = UIView()
                cameraContainer.layer.addSublayer(cameraPreviewLayer)
                cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                captureSession.startRunning()
                cameraContainer
            },
            onResize = { view: UIView, rect: CValue<CGRect> ->
                CATransaction.begin()
                CATransaction.setValue(true, kCATransactionDisableActions)
                view.layer.setFrame(rect)
                cameraPreviewLayer.setFrame(rect)
                CATransaction.commit()
            },
        )
//        CircularButton(
//            imageVector = IconPhotoCamera,
//            modifier = Modifier.align(Alignment.BottomCenter).padding(36.dp),
//            enabled = !capturePhotoStarted,
//        ) {
//            capturePhotoStarted = true
//            val photoSettings = AVCapturePhotoSettings.photoSettingsWithFormat(
//                format = mapOf(AVVideoCodecKey to AVVideoCodecTypeJPEG)
//            )
//            if (camera.position == AVCaptureDevicePositionFront) {
//                capturePhotoOutput.connectionWithMediaType(AVMediaTypeVideo)
//                    ?.automaticallyAdjustsVideoMirroring = false
//                capturePhotoOutput.connectionWithMediaType(AVMediaTypeVideo)
//                    ?.videoMirrored = true
//            }
//            capturePhotoOutput.capturePhotoWithSettings(
//                settings = photoSettings,
//                delegate = photoCaptureDelegate
//            )
//        }
        if (capturePhotoStarted) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp).align(Alignment.Center),
                color = Color.White.copy(alpha = 0.7f),
                strokeWidth = 8.dp,
            )
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun CLLocation.toGps() =
        GpsPosition(
            latitude = coordinate.useContents { latitude },
            longitude = coordinate.useContents { longitude }
        )
}
