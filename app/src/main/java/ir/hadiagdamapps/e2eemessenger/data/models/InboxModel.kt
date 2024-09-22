package ir.hadiagdamapps.e2eemessenger.data.models

import kotlinx.serialization.Serializable

@Serializable
data class InboxModel(
    val inboxId: Long,
    val publicKey: String,
    val encryptedPrivateKey: String, // AES encrypted
    var label: String = publicKey.replace("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE", ""),
    val salt: String? = null,
    val iv: String? = null
)