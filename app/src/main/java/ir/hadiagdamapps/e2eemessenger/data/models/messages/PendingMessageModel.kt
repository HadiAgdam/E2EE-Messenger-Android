package ir.hadiagdamapps.e2eemessenger.data.models.messages

data class PendingMessageModel(
    val messageId: Int,
    val inboxPublicKey: String,
    val recipientPublicKey: String,
    val encryptedKey: String,
    val message: String,
    val iv: String,
    val previewText: String,
    val previewIv: String
) {

    fun toPendingPreviewMessageModel(): PendingPreviewMessageModel =
        PendingPreviewMessageModel(
            id = messageId,
            encryptedMessage = previewText,
            iv = previewIv
        )

    fun toOutgoingMessage(): OutgoingMessage =
        OutgoingMessage(
            receiver = recipientPublicKey,
            encryptionKey = encryptedKey,
            encryptedMessage = message,
            iv = iv
        )
}