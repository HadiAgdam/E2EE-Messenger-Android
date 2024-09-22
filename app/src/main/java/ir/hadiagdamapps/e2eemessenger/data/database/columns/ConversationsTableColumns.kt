package ir.hadiagdamapps.e2eemessenger.data.database.columns

enum class ConversationsTableColumns {
    CONVERSATION_ID,
    INBOX_ID,
    SENDER_PUBLIC_KEY, // other person in conversation
    LAST_MESSAGE_ID,
    LABEL;

    override fun toString() = name.lowercase()
}