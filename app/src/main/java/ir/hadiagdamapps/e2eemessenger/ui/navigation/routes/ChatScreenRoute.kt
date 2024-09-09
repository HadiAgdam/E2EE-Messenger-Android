package ir.hadiagdamapps.e2eemessenger.ui.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
data class ChatScreenRoute(
    val publicKey: String,
    val privateKey: String,
    val recipientPublicKey: String,
)