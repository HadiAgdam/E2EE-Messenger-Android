package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.database.columns.InboxesTableColumns.*
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText


class InboxData(context: Context) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {

    private val table = Table.INBOXES

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
        while (isPublicKeyExists(pair.public.encoded.toText()))
            pair = E2EKeyGenerator.generateKeyPair()

        val salt = AesKeyGenerator.generateSalt()
        val aesKey = AesKeyGenerator.generateKey(pin, salt)

        val (privateKey, iv) = AesEncryptor.encryptMessage(pair.private.encoded.toText(), aesKey)


        var model = InboxModel(
            inboxId = 0,
            publicKey = pair.public.encoded.toText(),
            encryptedPrivateKey = privateKey,
            salt = salt,
            iv = iv,
            unseenMessageCount = 0
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
                    salt = c.getString(4),
                    iv = c.getString(5),
                    unseenMessageCount = c.getInt(6)
                )
            )
        while (c.moveToNext())

        c.close()
        return result
    }


    fun updateLabel(publicKey: String, label: String) {
        writableDatabase.update(
            table.tableName,
            ContentValues().apply { put(LABEL.toString(), label) },
            "$INBOX_PUBLIC_KEY = ?",
            arrayOf(publicKey)
        )
    }

    fun clearUnseen(publicKey: String) {
        TODO("set the conversation unseen messages count to 0")
    }

    // -------------------------------------------------------------------

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table.createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(table.dropQuery)
        onCreate(db)
    }
}