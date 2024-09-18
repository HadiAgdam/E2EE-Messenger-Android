package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.hadiagdamapps.e2eemessenger.data.models.InboxModel
import ir.hadiagdamapps.e2eemessenger.data.database.columns.InboxTableColumns.*
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesEncryptor
import ir.hadiagdamapps.e2eemessenger.data.encryption.aes.AesKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator
import ir.hadiagdamapps.e2eemessenger.data.encryption.e2e.E2EKeyGenerator.toText


class InboxData(context: Context) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {

    private val table = Table.INBOX

    private fun isPublicKeyExists(publicKey: String): Boolean {
        val query = "SELECT 1 FROM ${table.tableName} WHERE $INBOX_PUBLIC_KEY = ?"
        val cursor = writableDatabase.rawQuery(query, arrayOf(publicKey))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun delete(publicKey: String) =
        1 == writableDatabase.delete(table.tableName, "inbox_public_key = ?", arrayOf(publicKey))

    fun delete(model: InboxModel) = delete(model.publicKey)

    fun newInbox(pin: String): InboxModel {
        val values = ContentValues()

        var pair = E2EKeyGenerator.generateKeyPair()
        while (isPublicKeyExists(pair.public.encoded.toText()))
            pair = E2EKeyGenerator.generateKeyPair()

        val salt = AesKeyGenerator.generateSalt()
        val aesKey = AesKeyGenerator.generateKey(pin, salt)

        val (privateKey, iv) = AesEncryptor.encryptMessage(pair.private.encoded.toText(), aesKey)

        values.put(INBOX_PUBLIC_KEY.toString(), pair.public.encoded.toText())
        values.put(INBOX_PRIVATE_KEY.toString(), privateKey)
        values.put(LABEL.toString(), pair.public.encoded.toText())
        values.put(SALT.toString(), salt)
        values.put(IV.toString(), iv)

        writableDatabase.insert(table.tableName, null, values)
        writableDatabase.close()


        return InboxModel(
            pair.public.encoded.toText(),
            privateKey,
            pair.public.encoded.toText()
        )
    }

    fun getInboxes(): List<InboxModel> {
        val c = readableDatabase.rawQuery("SELECT * FROM ${table.tableName}", null)

        val result = ArrayList<InboxModel>()

        if (c.moveToFirst()) do
            result.add(
                InboxModel(
                    publicKey = c.getString(1),
                    encryptedPrivateKey =  c.getString(2),
                    label =  c.getString(3)
                )
            )
        while (c.moveToNext())

        c.close()
        return result
    }


    fun updateLabel(publicKey: String, label: String) {
        val values = ContentValues()

        values.put(LABEL.toString(), label)

        writableDatabase.update(table.tableName,values, "inbox_public_key = ?", arrayOf(publicKey))
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