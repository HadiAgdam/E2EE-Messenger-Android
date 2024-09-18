package ir.hadiagdamapps.e2eemessenger.data.models

import kotlinx.serialization.Serializable

@Serializable
data class InboxModel(
    val publicKey: String,
    val encryptedPrivateKey: String, // AES encrypted
    var label: String = publicKey
    var label: String = publicKey.replace("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE", ""),
)