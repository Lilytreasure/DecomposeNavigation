package platform

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.createChooser
import androidx.appcompat.app.AppCompatActivity

actual open class  LoadFiles(private val context: Context): AppCompatActivity(){
    private val intent = Intent()
        .setType("*/*")
        .setAction(ACTION_GET_CONTENT)
    actual fun loadData() {
        startActivityForResult(createChooser(intent, "Select a file"), 111)
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            onActivityResult(requestCode, resultCode, data)

            if (requestCode == 111 && resultCode == RESULT_OK) {
                val selectedFile = data?.data // The URI with the location of the file
            }
        }

    }

}