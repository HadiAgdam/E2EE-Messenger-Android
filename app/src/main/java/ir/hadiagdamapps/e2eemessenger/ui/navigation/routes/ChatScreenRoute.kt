package ir.hadiagdamapps.e2eemessenger.ui.navigation.routes

import kotlinx.serialization.Serializable
import javax.crypto.SecretKey

@Serializable
data class ChatScreenRoute(
//    val publicKey: String,
//    val privateKey: String,
//    val recipientPublicKey: String,
    val inboxPublicKey: String,
    val privateKey: String,
    val senderPublicKey: String,
    val aesKeyPin: String,
    val aesKeySalt: String,
    val conversationLabel: String
)