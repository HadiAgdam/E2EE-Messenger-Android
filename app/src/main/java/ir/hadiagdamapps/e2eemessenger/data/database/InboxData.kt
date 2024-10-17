package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ir.hadiagdamapps.e2eemessenger.data.database.columns.InboxesTableColumns
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.database.columns.InboxesTableColumns.*
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EEncryptor.toText
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText


class InboxData(context: Context): DatabaseHelper(context, Table.INBOXES){

    private fun isPublicKeyExists(publicKey: String): Boolean {
        val query = "SELECT 1 FROM ${table.tableName} WHERE $INBOX_PUBLIC_KEY = ?"
        val cursor = writableDatabase.rawQuery(query, arrayOf(publicKey))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun delete(publicKey: String) =
        1 == writableDatabase.delete(table.tableName, "$INBOX_PUBLIC_KEY = ?", arrayOf(publicKey))

    fun delete(model: InboxModel) = delete(model.publicKey)

    fun newInbox(pin: String): InboxModel {
        val values = ContentValues()

        var pair = E2EKeyGenerator.generateKeyPair()
        while (isPublicKeyExists(pair.public.toText()))
            pair = E2EKeyGenerator.generateKeyPair()

        val salt = AesKeyGenerator.generateSalt()
        val aesKey = AesKeyGenerator.generateKey(pin, salt)

        val (privateKey, iv) = AesEncryptor.encryptMessage(pair.private.toText(), aesKey)

        var model = InboxModel(
            inboxId = 0,
            publicKey = pair.public.toText(),
            encryptedPrivateKey = privateKey,
            salt = salt,
            iv = iv,
            unseenMessageCount = 0,
            lastMessageId = 0 // zero is right
        )

        values.put(INBOX_PUBLIC_KEY.toString(), model.publicKey)
        values.put(INBOX_PRIVATE_KEY.toString(), model.encryptedPrivateKey)
        values.put(LABEL.toString(), model.label)
        values.put(SALT.toString(), model.salt)
        values.put(IV.toString(), model.iv)

        val db = writableDatabase
        model = model.copy(inboxId = db.insert(table.tableName, null, values))
        db.close()


        return model
    }

    fun getInboxes(): List<InboxModel> {
        val c = readableDatabase.rawQuery("SELECT * FROM ${table.tableName}", null)

        val result = ArrayList<InboxModel>()

        if (c.moveToFirst()) do
            result.add(
                InboxModel(
                    inboxId = c.getLong(0),
                    publicKey = c.getString(1),
                    encryptedPrivateKey = c.getString(2),
                    label = c.getString(3),
                    salt = c.getString(5),
                    iv = c.getString(4),
                    unseenMessageCount = c.getInt(6),
                    lastMessageId = c.getInt(7)
                )
            )
        while (c.moveToNext())

        c.close()
        return result
    }

    fun updateLabel(publicKey: String, label: String) {
        writableDatabase.apply {
            update(
                table.tableName,
                ContentValues().apply { put(LABEL, label) },
                "$INBOX_PUBLIC_KEY = ?",
                arrayOf(publicKey)
            )
            close()
        }
    }

    fun clearUnseen(publicKey: String) {
        writableDatabase.update(
            table.tableName,
            ContentValues().apply { put(UNSEEN_MESSAGE_COUNT, 0) },
            "$INBOX_PUBLIC_KEY = ?",
            arrayOf(publicKey)
        )
    }

    fun increaseUnseenMessageCount(inboxId: Long, increment: Int) {
        readableDatabase.rawQuery(
            "select $UNSEEN_MESSAGE_COUNT from ${table.tableName} where $INBOX_ID = ?",
            arrayOf(inboxId.toString())
        ).apply {
            if (moveToFirst()) {
                val count = getInt(0) + increment
                writableDatabase.apply {
                    update(
                        table.tableName,
                        ContentValues().apply { put(UNSEEN_MESSAGE_COUNT, count) },
                        "$INBOX_ID = ?",
                        arrayOf(inboxId.toString())
                    )
                    close()
                }
            }
            this.close()
        }
    }

    fun updateLastMessageId(inboxId: Long, lastMessageId: Int) {
        writableDatabase.apply {
            update(
                table.tableName,
                ContentValues().apply { put(LAST_MESSAGE_ID, lastMessageId) },
                "$INBOX_ID = ?",
                arrayOf(inboxId.toString())
            )
            close()
        }
    }

    fun getInboxById(inboxPublicKey: String): InboxModel? {
        getInboxes().forEach {
            if (it.publicKey == inboxPublicKey) return it
        }
        return null
    }

}

private fun ContentValues.put(key: InboxesTableColumns, value: Int) {
    put(key.toString(), value)
}

private fun ContentValues.put(key: InboxesTableColumns, value: String) {
    put(key.toString(), value)
}
