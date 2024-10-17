package ir.hadiagdamapps.e2eemessenger.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class Clipboard(private val context: Context) {

    // copy text to clipboard
    fun copy(text: String, label: String = "E2EE-Messenger") {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
            setPrimaryClip(ClipData.newPlainText(label, text))
        }
    }

    fun readClipboard(): String {
        TODO("read the content of clipboard")
    }
}