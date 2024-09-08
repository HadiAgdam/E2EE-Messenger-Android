package ir.hadiagdamapps.e2eemessenger.data.models.messages

import ir.hadiagdamapps.e2eemessenger.data.models.PublicKey

data class OutgoingMessage(
    val receiver: PublicKey,
    val content: MessageContent
)