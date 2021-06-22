package be.stefan.event.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import be.stefan.event.models.Event


class EventDao {

    private var context: Context
    private lateinit var db : SQLiteDatabase
    private lateinit var dbHelper: DbHelper

    constructor(context: Context) {
        this.context = context
    }

    fun openWritable(): EventDao {
        dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
        return this
    }

    fun openReadable(): EventDao {
        dbHelper = DbHelper(context)
        db = dbHelper.readableDatabase
        return this
    }

    fun close() {
        db.close()
        dbHelper.close()
    }

    // CRUD

    private fun createContentValues(item : Event) : ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DbInfo.COLUMN_TITLE, item.title)
        contentValues.put(DbInfo.COLUMN_TIME, item.time)
        contentValues.put(DbInfo.COLUMN_DESC, item.desc)
        contentValues.put(DbInfo.COLUMN_ADDRESS, item.address)
        return contentValues
    }

    fun insert(item: Event) : Long {
        val contentValues = createContentValues(item)
        return db.insert(DbInfo.TABLE_NAME, null, contentValues)
    }


}