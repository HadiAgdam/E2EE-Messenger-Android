package ir.hadiagdamapps.e2eemessenger.data.models.messages


data class OutgoingMessage(
    val receiver: String,
    val encryptionKey: String,
    val encryptedMessage: String
)