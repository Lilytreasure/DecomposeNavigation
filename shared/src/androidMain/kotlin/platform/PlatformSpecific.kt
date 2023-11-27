package platform

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

actual open class PlatformSpecific(private val context: Context) : AppCompatActivity() {
    private val PICK_PDF_FILE = 2
    private val PICK_FILE_REQUEST_CODE = 123
    private val currentActivity: AppCompatActivity = (context as AppCompatActivity)

    //private var createFileLauncher: ActivityResultLauncher<Intent>

//    init {
//        // Initialize the ActivityResultLauncher in the init block
//        createFileLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                handleFileSelectionResult(result.resultCode, result.data?.data)
//            }
//    }

    private fun openFile(initialUri: Uri? = null) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
        }

        // Use registerForActivityResult to obtain a launcher
        val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleFileSelectionResult(result.resultCode, result.data?.data)
        }

       currentActivity.startActivityForResult(intent,123)
    }

    actual fun loadData() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            type = "*/*" // Specify the MIME type of the files you want to allow (e.g., "application/pdf")
//        }

        // Start the activity for result using the ActivityResultLauncher
        //createFileLauncher.launch(intent)
        //startActivity(intent)

        openFile()
    }

//    private fun handleFileSelectionResult(resultCode: Int, selectedFileUri: Uri?) {
//        if (resultCode == AppCompatActivity.RESULT_OK) {
//            // Handle the result, for example, you can get the selected file URI
//            if (selectedFileUri != null) {
//                println("File loaded: $selectedFileUri")
//                // Process the selected file URI as needed
//            }
//        } else {
//            // The user canceled or encountered an error
//            println("File selection canceled or encountered an error")
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedFileUri ->
                // Get the file name from the URI
                val fileName = getFileName(selectedFileUri)

                // Print or use the file name as needed
                println("Selected file name: $fileName")
            }
        }else{

            println("Error opening")

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
