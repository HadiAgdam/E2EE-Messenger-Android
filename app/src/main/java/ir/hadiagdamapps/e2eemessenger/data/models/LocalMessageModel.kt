package ir.hadiagdamapps.e2eemessenger.data.models

data class LocalMessageModel (
    val messageId: Long, // don't know if needed
    val conversationId: Int,
    val text: String, // contains both encrypted and decrypted text
    val timestamp: Long,
    val sent: Boolean, // sent or received
    val iv: String, // for encryption
)