package ir.hadiagdamapps.e2eemessenger.data.models

data class ConversationModel(
    val id: Int,
    val label: String,
    val lastMessage:  LocalMessageModel,
    val senderPublicKey: String,
    val unseenMessageCount: Int
)
