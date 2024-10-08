package ir.hadiagdamapps.e2eemessenger.data.database.columns

enum class InboxesTableColumns {
    INBOX_ID,
    INBOX_PUBLIC_KEY,
    INBOX_PRIVATE_KEY,
    LABEL,
    IV,
    SALT,
    UNSEEN_MESSAGE_COUNT,
    LAST_MESSAGE_ID,
    ;

    override fun toString() = name.lowercase()

}