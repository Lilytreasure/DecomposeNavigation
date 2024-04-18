import androidx.compose.ui.graphics.ImageBitmap
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType
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
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraDevice
import platform.UIKit.UIImagePickerControllerSourceType
import platform.darwin.NSObject
import platform.UniformTypeIdentifiers.UTTypeImage
import platform.darwin.NSInteger


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
        //Todo-------
//
//		val url = NSURL( "tel://9809088798")
//
//        if (UIApplication.sharedApplication().canOpenURL(url))
//        {
//            UIApplication.sharedApplication().openURL(url)
//        }
//    }
        val urlString = "tel://$phoneNumber"
        val url = NSURL(string = urlString)
//        val url =
//            NSURL(string = "tel://(phoneNumber)")

        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }

    }
}
