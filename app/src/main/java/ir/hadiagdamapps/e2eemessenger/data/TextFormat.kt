package ir.hadiagdamapps.e2eemessenger.data

import androidx.core.text.isDigitsOnly
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TextFormat {

    const val PIN_LENGTH = 6


    fun isValidPin(text: String?): Boolean =
        (text ?: ("")).length == PIN_LENGTH && (text ?: ("")).isDigitsOnly()


    fun timestampToText(timeStamp: Long): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(timeStamp))


    fun isValidLabel(text: String): Boolean = true // TODO


    fun isValidPublicKey(text: String): Boolean {
        return try {
            E2EKeyGenerator.getPublicKeyFromString(text)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun isValidMessage(text: String?): Boolean{
        return text != null && text.length < 500 // TODO
    }
}