package ir.hadiagdamapps.e2eemessenger.data.models

import ir.hadiagdamapps.e2eemessenger.data.models.messages.LocalMessageModel

data class ConversationModel(
    val id: Int,
    val label: String,
    val lastMessage: LocalMessageModel,
    val senderPublicKey: String,
    val unseenMessageCount: Int
)
