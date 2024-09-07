package ir.hadiagdamapps.e2eemessenger.data.models

data class EncryptedMessage(val senderPublicKey: PublicKey, val message: String) {
    override fun toString(): String {
        TODO()
    }
}