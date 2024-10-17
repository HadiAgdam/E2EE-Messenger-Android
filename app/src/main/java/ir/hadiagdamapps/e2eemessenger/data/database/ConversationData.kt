package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ir.hadiagdamapps.e2eemessenger.data.database.columns.ConversationsTableColumns
import ir.hadiagdamapps.e2eemessenger.data.database.columns.ConversationsTableColumns.*
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel


class ConversationData(context: Context) : DatabaseHelper(context, Table.CONVERSATIONS){

    private val localMessageData = LocalMessageData(context)

    fun loadConversations(inboxId: Long): List<ConversationModel> {
        val result = ArrayList<ConversationModel>()
        val c = readableDatabase.rawQuery(
            "SELECT * FROM ${table.tableName} where $INBOX_ID = ?", arrayOf(inboxId.toString())
        )

        if (c.moveToFirst()) do {
            result.add(
                ConversationModel(
                    id = c.getLong(0).toInt(),
                    label = c.getString(4),
                    lastMessage = localMessageData.getMessageById(c.getLong(3)) ?: continue,
                    senderPublicKey = c.getString(2),
                    unseenMessageCount = c.getInt(5)
                ).apply {
                    Log.e(
                        "last message text-------------------------------------",
                        lastMessage.text
                    )
                }
            )

        } while (c.moveToNext())

        c.close()
        return result
    }

    fun updateLabel(conversationId: Int, newLabel: String) {
        writableDatabase.update(
            table.tableName,
            ContentValues().apply { put(LABEL.toString(), newLabel) },
            "$CONVERSATION_ID = ?",
            arrayOf(conversationId.toString())
        )
    }

    fun delete(conversationId: Int) {
        writableDatabase.delete(
            table.tableName, "$CONVERSATION_ID = ?", arrayOf(conversationId.toString())
        )
    }

    fun clearUnseen(publicKey: String) {
        writableDatabase.update(
            table.tableName,
            ContentValues().apply { put(UNSEEN_MESSAGE_COUNT, 0) },
            "$SENDER_PUBLIC_KEY = ?",
            arrayOf(publicKey)
        )
    }

    fun getConversationIdByPublicKey(publicKey: String): Int? {
        val c = readableDatabase.rawQuery(
            "SELECT $CONVERSATION_ID FROM ${table.tableName} where $SENDER_PUBLIC_KEY = ?",
            arrayOf(publicKey)
        )

        if (c.moveToFirst()) {
            val id = c.getLong(0).toInt()
            c.close()
            return id
        }

        c.close()

        return null
    }

    fun newConversation(
        inboxId: Long,
        publicKey: String,
    ): Int {
        return writableDatabase.insert(table.tableName, null, ContentValues().apply {
            put(INBOX_ID, inboxId)
            put(SENDER_PUBLIC_KEY, publicKey)
            put(
                LABEL, publicKey.replace("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE", "")
            ) // This is wrong
        }).toInt()
    }

    fun updateLastMessage(
        conversationId: Int, newLastMessageId: Long
    ) {
        writableDatabase.apply {
            update(
                table.tableName,
                ContentValues().apply { put(LAST_MESSAGE_ID, newLastMessageId) },
                "$CONVERSATION_ID = ?",
                arrayOf(conversationId.toString())
            )
            close()
        }
    }

    fun incrementUnseenCount(conversationId: Int) {
        val count = getUnseenCount(conversationId) + 1
        Log.e("incremented", count.toString())
        writableDatabase.apply {
            update(
                table.tableName,
                ContentValues().apply { put(UNSEEN_MESSAGE_COUNT, count) },
                "$CONVERSATION_ID = ?",
                arrayOf(conversationId.toString())
            )
            close()
        }
    }

    private fun getUnseenCount(conversationId: Int): Int {
        val c = readableDatabase.rawQuery(
            "SELECT * FROM ${table.tableName} where $CONVERSATION_ID = ?", arrayOf(conversationId.toString())
        )

        if (c.moveToFirst()) {
            val count = c.getInt(5)
            c.close()
            return count
        }


        c.close()
        return -1
    }

}

private fun ContentValues.put(key: ConversationsTableColumns, value: String) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: ConversationsTableColumns, value: Long) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: ConversationsTableColumns, value: Int) {
    put(key.toString(), value)
}
