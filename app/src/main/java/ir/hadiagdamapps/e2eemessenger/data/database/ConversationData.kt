package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.e2eemessenger.data.database.columns.ConversationsTableColumns.*
import ir.hadiagdamapps.e2eemessenger.data.models.ConversationModel
import ir.hadiagdamapps.e2eemessenger.data.models.LocalMessageModel


class ConversationData(context: Context) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {

    private val table = Table.CONVERSATIONS
    private val localMessageData = LocalMessageData(context)

    fun loadConversations(inboxId: Long): List<ConversationModel> {
        val result = ArrayList<ConversationModel>()
        val c = readableDatabase.rawQuery("SELECT * FROM ${table.tableName} where $INBOX_ID", arrayOf(inboxId.toString()))

        if (c.moveToFirst()) do {
            result.add(
                ConversationModel(
                    id = c.getLong(0).toInt(),
                    label = c.getString(4),
                    lastMessage = localMessageData.getMessageById(c.getLong(3)),
                    senderPublicKey = c.getString(2)
                )
            )
        } while (c.moveToNext())


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

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table.createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(table.dropQuery)
        onCreate(db)
    }

}