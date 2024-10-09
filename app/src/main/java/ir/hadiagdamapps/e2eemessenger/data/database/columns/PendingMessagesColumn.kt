package ir.hadiagdamapps.e2eemessenger.data.database.columns

enum class PendingMessagesColumn {
    PENDING_MESSAGE_ID,
    INBOX_PUBLIC_KEY,
    RECIPIENT_PUBLIC_KEY,
    ENCRYPTED_KEY,
    MESSAGE,
    IV,
    PREVIEW_TEXT,
    PREVIEW_IV;


    override fun toString() = name.lowercase()
}