package ir.hadiagdamapps.e2eemessenger.data

import androidx.core.text.isDigitsOnly
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
        TODO()
    }

    fun isValidMessage(text: String): Boolean{
        TODO("validate if the message user wants so send")
    }
}