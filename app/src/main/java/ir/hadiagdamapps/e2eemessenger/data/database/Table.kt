package ir.hadiagdamapps.e2eemessenger.data.database

import ir.hadiagdamapps.e2eemessenger.data.database.columns.*


enum class Table(
    val tableName: String, val createQuery: String, val dropQuery: String
) {
    INBOXES(
        tableName = "inboxes",
        createQuery = """
            CREATE TABLE inboxes (

                ${InboxesTableColumns.INBOX_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${InboxesTableColumns.INBOX_PUBLIC_KEY} TEXT NOT NULL UNIQUE,
                ${InboxesTableColumns.INBOX_PRIVATE_KEY} TEXT NOT NULL,
                ${InboxesTableColumns.LABEL} TEXT,
                ${InboxesTableColumns.IV} TEXT,
                ${InboxesTableColumns.SALT} TEXT,
                ${InboxesTableColumns.UNSEEN_MESSAGE_COUNT} INTEGER,
                ${InboxesTableColumns.LAST_MESSAGE_ID} INTEGER
            )
            """,
        dropQuery = "DROP TABLE IF EXISTS inboxes",
    ),

    LOCAL_MESSAGES(
        tableName = "local_messages",
        createQuery = """
            
            CREATE TABLE messages (
                ${LocalMessagesTableColumn.MESSAGE_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${LocalMessagesTableColumn.CONVERSATION_ID} INTEGER NOT NULL,
                ${LocalMessagesTableColumn.TEXT} TEXT,
                ${LocalMessagesTableColumn.TIME_STAMP} LONG,
                ${LocalMessagesTableColumn.SENT} BOOLEAN,
                ${LocalMessagesTableColumn.IV} TEXT,
                FOREIGN KEY (${LocalMessagesTableColumn.CONVERSATION_ID}) PREFERENCES conversations(${ConversationsTableColumns.CONVERSATION_ID})
            )
            
        """, dropQuery = "DROP TABLE IF EXISTS messages"
    ),

    CONVERSATIONS(
        tableName = "conversations", createQuery = """
            CREATE TABLE conversations (
                ${ConversationsTableColumns.CONVERSATION_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ConversationsTableColumns.INBOX_ID} INTEGER NOT NULL,
                ${ConversationsTableColumns.SENDER_PUBLIC_KEY} TEXT NOT NULL UNIQUE,
                ${ConversationsTableColumns.LAST_MESSAGE_ID} INTEGER,
                ${ConversationsTableColumns.LABEL} TEXT,
                ${ConversationsTableColumns.UNSEEN_MESSAGE_COUNT} INTEGER,
                FOREIGN KEY (${ConversationsTableColumns.INBOX_ID}) REFERENCES ${INBOXES.tableName}(${InboxesTableColumns.INBOX_ID}),
                FOREIGN KEY (${ConversationsTableColumns.LAST_MESSAGE_ID}) REFERENCES ${LOCAL_MESSAGES.tableName}(${LocalMessagesTableColumn.MESSAGE_ID})
            )

            
        """.trimIndent(), dropQuery = "DROP TABLE IF EXISTS conversations"
    ),

}

