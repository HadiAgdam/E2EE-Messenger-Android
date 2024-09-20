package ir.hadiagdamapps.e2eemessenger.data.models

data class ConversationModel(
    val id: Int,
    val label: String,
    val lastMessage:  MessageModel
)
