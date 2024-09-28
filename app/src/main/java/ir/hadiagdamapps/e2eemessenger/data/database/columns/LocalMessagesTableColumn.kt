package ir.hadiagdamapps.e2eemessenger.data.database.columns

enum class LocalMessagesTableColumn {
    MESSAGE_ID,
    CONVERSATION_ID,
    TEXT, // encrypted
    TIME_STAMP,
    SENT,
    IV
}