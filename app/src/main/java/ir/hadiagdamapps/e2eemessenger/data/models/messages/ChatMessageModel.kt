package ir.hadiagdamapps.e2eemessenger.data.models.messages

data class ChatMessageModel(
    val text: String,
    val sent: Boolean,
    val time: Long
)