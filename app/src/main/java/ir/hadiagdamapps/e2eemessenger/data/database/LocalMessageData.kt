package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.e2eemessenger.data.database.columns.LocalMessagesTableColumn
import ir.hadiagdamapps.e2eemessenger.data.models.messages.LocalMessageModel
import ir.hadiagdamapps.e2eemessenger.data.database.columns.LocalMessagesTableColumn.*
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.models.messages.ChatMessageModel
import javax.crypto.SecretKey


class LocalMessageData(context: Context) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {

    private val table = Table.LOCAL_MESSAGES

    fun getMessageById(messageId: Long): LocalMessageModel? {
        val c = readableDatabase.rawQuery(
            "SELECT * FROM ${table.tableName} where $MESSAGE_ID = ?", arrayOf(messageId.toString())
        )

        if (c.moveToFirst()) {
            val model = LocalMessageModel(
                messageId = c.getLong(0),
                conversationId = c.getInt(1),
                text = c.getString(2),
                timestamp = c.getLong(3),
                sent = c.getInt(4) == 1,
                iv = c.getString(5)
            )
            c.close()
            return model
        }

        return null
    }

    fun insertNewMessage(
        conversationId: Int, text: String, timestamp: Long, sent: Boolean, iv: String
    ): Long {
        return writableDatabase.insert(table.tableName, null, ContentValues().apply {
            put(CONVERSATION_ID, conversationId)
            put(TEXT, text)
            put(TIME_STAMP, timestamp)
            put(SENT, sent)
            put(IV, iv)
        })
    }

    fun getConversationMessages(conversationId: Int, aesKey: SecretKey): List<ChatMessageModel> {
        val result = ArrayList<ChatMessageModel>()

        val c = readableDatabase.rawQuery(
            "SELECT $TEXT, $SENT, $TIME_STAMP, $IV FROM ${table.tableName} WHERE $CONVERSATION_ID = ?",
            arrayOf(conversationId.toString())
        )

        if (c.moveToFirst()) do result.add(
            ChatMessageModel(
                text = AesEncryptor.decryptMessage(c.getString(0), aesKey, c.getString(3))
                    ?: continue,
                sent = c.getInt(1) == 1,
                time = c.getLong(2)
            )
        )
        while (c.moveToNext())

        c.close()
        return result
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table.createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(table.dropQuery)
        onCreate(db)
    }
}

private fun ContentValues.put(key: LocalMessagesTableColumn, value: String) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: LocalMessagesTableColumn, value: Long) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: LocalMessagesTableColumn, value: Int) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: LocalMessagesTableColumn, value: Boolean) {
    put(key.toString(), value)
}
