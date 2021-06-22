package be.stefan.event.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper : SQLiteOpenHelper {

    constructor(context: Context) : super(context, DbInfo.DB_NAME, null, DbInfo.DB_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(DbInfo.REQUEST_CREATE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL(DbInfo.REQUEST_DELETE)
        }
        onCreate(db)
    }
}