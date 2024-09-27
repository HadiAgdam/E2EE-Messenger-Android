package ir.hadiagdamapps.e2eemessenger.data.database

import ir.hadiagdamapps.e2eemessenger.data.database.columns.*


enum class Table(
    val tableName: String,
    val createQuery: String,
    val dropQuery: String,
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
                ${InboxesTableColumns.UNSEEN_MESSAGE_COUNT} INTEGER
            )
            """,
        dropQuery = "DROP TABLE IF EXISTS inboxes",
    ),

    MESSAGES(
        tableName = "messages",
        createQuery = """
            
            CREATE TABLE messages (

            
            
            )
            
        """,
        dropQuery = "DROP IF EXISTS messages"
    ),

    CONVERSATIONS(
    tableName = "conversations",
    createQuery = """
            CREATE TABLE inboxes (
            
                ${ConversationsTableColumns.CONVERSATION_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ConversationsTableColumns.INBOX_ID} INTEGER NOT NULL,
                ${ConversationsTableColumns.SENDER_PUBLIC_KEY} NOT NULL UNIQUE,
                ${ConversationsTableColumns.LAST_MESSAGE_ID} INTEGER,
                ${ConversationsTableColumns.LABEL} TEXT,
                ${ConversationsTableColumns.UNSEEN_MESSAGE_COUNT} INTEGER,
                FOREIGN KEY (${ConversationsTableColumns.INBOX_ID}) PREFERENCES ${INBOXES.tableName}(${InboxesTableColumns.INBOX_ID}),
                FOREIGN KEY (${ConversationsTableColumns.LAST_MESSAGE_ID}) PREFERENCES ${MESSAGES.tableName}(${MessagesTableColumn.MESSAGE_ID})
            
            
        """.trimIndent(),
    dropQuery = "DROP TABLE if EXISTS conversation"
    ),

}

