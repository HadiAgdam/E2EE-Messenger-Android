package ir.hadiagdamapps.e2eemessenger.data.database

import ir.hadiagdamapps.e2eemessenger.data.database.columns.InboxTableColumns.*


enum class Table(
    val tableName: String,
    val createQuery: String,
    val dropQuery: String,
) {
    INBOX(
        tableName = "inboxes",
        createQuery = """
            CREATE TABLE inboxes (

                $INBOX_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $INBOX_PUBLIC_KEY TEXT NOT NULL UNIQUE,
                $INBOX_PRIVATE_KEY TEXT NOT NULL,
                $LABEL TEXT,
                $IV TEXT,
                $SALT TEXT
            )
            """,
        dropQuery = "DROP TABLE IF EXISTS inboxes",
    )

}

