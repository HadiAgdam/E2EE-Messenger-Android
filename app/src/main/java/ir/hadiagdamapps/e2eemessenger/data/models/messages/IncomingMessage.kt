package ir.hadiagdamapps.e2eemessenger.data.models.messages

import ir.hadiagdamapps.e2eemessenger.data.models.EncryptionKey

data class IncomingMessage(
    val messageId: Int,
    val key: EncryptionKey,
    val messageContent: MessageContent,
    val time: Long
)