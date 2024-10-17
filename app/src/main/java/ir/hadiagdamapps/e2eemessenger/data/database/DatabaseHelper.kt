package ir.hadiagdamapps.e2eemessenger.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseHelper(context: Context, val table: Table) :
    SQLiteOpenHelper(context, MessengerDatabase.DB_NAME, null, MessengerDatabase.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { d ->
            Table.entries.forEach { table ->
                d.execSQL(table.createQuery)
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let { d ->
            Table.entries.forEach { table ->
                d.execSQL(table.dropQuery)
            }
            onCreate(d)
        }
    }
}