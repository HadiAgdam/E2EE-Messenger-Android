package ir.hadiagdamapps.e2eemessenger.data.models

data class ChatPreviewModel(
    val inboxPublicKey: String,
    val senderPublicKey: String,
    val label: String,
    val lastMessage: String,
    val time: String,
    val sent: Boolean
)
