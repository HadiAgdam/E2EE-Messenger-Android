package ir.hadiagdamapps.e2eemessenger.data

import androidx.core.text.isDigitsOnly

object TextFormat {

    const val PIN_LENGTH = 6


    fun isValidPin(text: String?): Boolean =
        (text ?: ("")).length == PIN_LENGTH && (text ?: ("")).isDigitsOnly()


}