package be.stefan.event.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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

    private fun cursor2Item(cursor : Cursor) : Event {
        val id = cursor.getLong(cursor.getColumnIndex(EventTb.COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndex(EventTb.COLUMN_TITLE))
        val time = cursor.getString(cursor.getColumnIndex(EventTb.COLUMN_TIME))
        val desc = cursor.getString(cursor.getColumnIndex(EventTb.COLUMN_DESC))
        val address = cursor.getString(cursor.getColumnIndex(EventTb.COLUMN_ADDRESS))
        val item = Event(id, title, time, desc, address)
        return item
    }

    private fun createContentValues(item : Event) : ContentValues {
        val contentValues = ContentValues()
        contentValues.put(EventTb.COLUMN_TITLE, item.title)
        contentValues.put(EventTb.COLUMN_TIME, item.time.toString())
        contentValues.put(EventTb.COLUMN_DESC, item.desc)
        contentValues.put(EventTb.COLUMN_ADDRESS, item.address)
        return contentValues
    }

    fun insert(item: Event) : Long {
        val contentValues = createContentValues(item)
        return db.insert(EventTb.TABLE_NAME, null, contentValues)
    }

    fun searchEguals(item: Event) : Boolean {
        val cursor = db.query(
            EventTb.TABLE_NAME,
            null,
            EventTb.COLUMN_TITLE + "= ? AND " +
                    EventTb.COLUMN_TIME + "= ? AND " +
                    EventTb.COLUMN_DESC +"= ? AND " +
                    EventTb.COLUMN_ADDRESS + "= ?",
            arrayOf(item.title, item.time, item.desc, item.address),
            null,
            null,
            null
        )
        if (cursor.count > 0) { return true }
        return false
    }

    fun readItem(id : Long) : Event? {
        val cursor = db.query(
            EventTb.TABLE_NAME,
            null,
            EventTb.COLUMN_ID + "= ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.count > 0) {
            cursor.moveToFirst()
            return cursor2Item(cursor)
        }

        return null
    }

    fun allItems() : List<Event>? {
        val cursor = db.query(
            EventTb.TABLE_NAME,
            null,
            "datetime(${EventTb.COLUMN_TIME}) > datetime('now')",
            null,
            null,
            null,
            EventTb.COLUMN_TIME +" ASC"
        )

        if (cursor.count > 0) {
            cursor.moveToFirst()
            val list : MutableList<Event> = mutableListOf()
            while (!cursor.isAfterLast) {
                list.add(cursor2Item(cursor))
                cursor.moveToNext()
            }
            return list.toList()
        }

        return null
    }

    fun update(id : Long) : Boolean {
        val contentValues = ContentValues()
        val nbRow = db.update(
            EventTb.TABLE_NAME,
            contentValues,
            EventTb.COLUMN_ID + " = ?",
            arrayOf(id.toString())
        )

        if (nbRow == 1) { return true }
        return false
    }

    fun delete(id : Long) : Boolean {
        val nbRow = db.delete(
            EventTb.TABLE_NAME,
            EventTb.COLUMN_ID + " = ?",
            arrayOf(id.toString())
        )

        if (nbRow == 1) { return true }
        return false
    }
}