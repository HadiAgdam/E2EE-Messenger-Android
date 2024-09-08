package ir.hadiagdamapps.e2eemessenger.data.models.messages



data class IncomingMessage(
    val messageId: Int,
    val encryptionKey: String,
    val encryptedMessage: String,
    val time: Double
)