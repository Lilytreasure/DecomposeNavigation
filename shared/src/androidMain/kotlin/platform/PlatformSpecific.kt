package platform

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.atwa.filepicker.core.FilePicker
import java.io.File

actual open class PlatformSpecific(private val context: Context) : AppCompatActivity() {
    private val PICK_PDF_FILE = 2
    private val PICK_FILE_REQUEST_CODE = 123
    private val currentActivity: AppCompatActivity = (context as AppCompatActivity)
    private val filePicker = FilePicker.getInstance(currentActivity)
    //private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private val CAMERA_PERMISSION_REQUEST_CODE = 123
    val filePickerLauncher =
        currentActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleFileSelectionResult(result.resultCode, result.data?.data)
        }
    actual fun loadFiles(callback: (String?) -> Unit) {
        // Use the file picker to pick a PDF file
        filePicker.pickPdf { meta ->
            // Get the selected file name
            val name = meta?.name

            // Perform other actions with the file metadata if needed
            val sizeKb: Int? = meta?.sizeKb
            val file: File? = meta?.file
            println("Loaded File name: $name")

            // Invoke the callback with the selected file name
            callback(name)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedFileUri ->
                // Get the file name from the URI
                val fileName = getFileName(selectedFileUri)

                // Print or use the file name as needed
                println("Selected file name: $fileName")
            }
        } else {

            println("Error opening")

        }
    }

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

}
