package ir.hadiagdamapps.e2eemessenger.data.models

data class InboxModel(
    val publicKey: String,
    val encryptedPrivateKey: String, // AES encrypted
    var label: String = publicKey
)