package ir.hadiagdamapps.e2eemessenger.data.models.messages

import ir.hadiagdamapps.e2eemessenger.data.models.EncryptionKey
import ir.hadiagdamapps.e2eemessenger.data.models.PublicKey

data class MessageContent(
    val senderPublicKey: PublicKey,
    val text: String
) {
    fun encrypt(encryptionKey: EncryptionKey): String = TODO("return an AES encrypted json")
}