package ir.hadiagdamapps.e2eemessenger.data.models.messages

data class PendingPreviewMessageModel(
    val id: Int,
    val encryptedMessage: String,
    val iv: String
)