package com.example.a24012011115_mad_practical_7

import android.content.ContentValues
import android.database.Cursor

object PersonDbTableData {
    const val TABLE_NAME = "persons"

    const val COL_ID = "id"
    const val COL_NAME = "name"
    const val COL_EMAIL = "email"
    const val COL_PHONE = "phone"
    const val COL_ADDRESS = "address"

    const val COLUMN_ID = COL_ID
    const val COLUMN_NAME = COL_NAME
    const val COLUMN_EMAIL = COL_EMAIL
    const val COLUMN_PHONE = COL_PHONE
    const val COLUMN_ADDRESS = COL_ADDRESS

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COL_ID TEXT PRIMARY KEY, " +
            "$COL_NAME TEXT, " +
            "$COL_EMAIL TEXT, " +
            "$COL_PHONE TEXT, " +
            "$COL_ADDRESS TEXT)"

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun personToContentValues(person: Person): ContentValues {
        val values = ContentValues()
        values.put(COL_ID, person.id)
        values.put(COL_NAME, person.name)
        values.put(COL_EMAIL, person.emailId)
        values.put(COL_PHONE, person.phoneNo)
        values.put(COL_ADDRESS, person.address)
        return values
    }

    fun cursorToPerson(cursor: Cursor): Person {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(COL_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS))
        return Person(id, name, email, phone, address)
    }
}
