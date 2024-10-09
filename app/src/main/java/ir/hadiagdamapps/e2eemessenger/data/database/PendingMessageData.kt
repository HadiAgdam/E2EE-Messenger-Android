package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.e2eemessenger.data.database.columns.PendingMessagesColumn
import ir.hadiagdamapps.e2eemessenger.data.database.columns.PendingMessagesColumn.*
import ir.hadiagdamapps.e2eemessenger.data.models.messages.PendingMessageModel

class PendingMessageData(context: Context) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {
    private val table = Table.PENDING_MESSAGES


    fun newPendingMessage(
        inboxPublicKey: String,
        recipientPublicKey: String,
        encryptedKey: String,
        message: String,
        iv: String,
        previewText: String,
        previewIv: String
    ): Int =
        writableDatabase.insert(table.tableName, null, ContentValues().apply {
            put(INBOX_PUBLIC_KEY, inboxPublicKey)
            put(RECIPIENT_PUBLIC_KEY, recipientPublicKey)
            put(ENCRYPTED_KEY, encryptedKey)
            put(MESSAGE, message)
            put(IV, iv)
            put(PREVIEW_TEXT, previewText)
            put(PREVIEW_IV, previewIv)
        }).apply {
            close()
        }.toInt()


    fun getPendingMessages(
        inboxPublicKey: String,
        recipientPublicKey: String
    ) = ArrayList<PendingMessageModel>().apply {

        val c = readableDatabase.rawQuery(
            "SELECT " +
                    "$PENDING_MESSAGE_ID," +
                    "$INBOX_PUBLIC_KEY," +
                    "$RECIPIENT_PUBLIC_KEY," +
                    "$ENCRYPTED_KEY," +
                    "$MESSAGE," +
                    "$IV," +
                    "$PREVIEW_TEXT," +
                    "$PREVIEW_IV," +
                    "from ${table.tableName} where $INBOX_PUBLIC_KEY = ?, $RECIPIENT_PUBLIC_KEY = ? ",
            arrayOf(inboxPublicKey, recipientPublicKey)
        )

        if (c.moveToFirst()) do
            add(
                PendingMessageModel(
                    messageId = c.getInt(0),
                    inboxPublicKey = c.getString(1),
                    recipientPublicKey = c.getString(2),
                    encryptedKey = c.getString(3),
                    message = c.getString(4),
                    iv = c.getString(5),
                    previewText = c.getString(6),
                    previewIv = c.getString(7)
                )
            )
        while (c.moveToNext())

        c.close()
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table.createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(table.dropQuery)
        onCreate(db)
    }
}

private fun ContentValues.put(key: PendingMessagesColumn, value: Int) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: PendingMessagesColumn, value: String) {
    put(key.toString(), value)
}