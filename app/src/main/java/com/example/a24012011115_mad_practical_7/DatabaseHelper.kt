package com.example.a24012011115_mad_practical_7

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "persons_db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(PersonDbTableData.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + PersonDbTableData.TABLE_NAME)
        onCreate(db)
    }

    fun insertPerson(person: Person): Long {
        return writableDatabase.insertWithOnConflict(
            PersonDbTableData.TABLE_NAME, null, getValues(person),
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun insertAllPersons(personList: List<Person>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (person in personList) {
                db.insertWithOnConflict(
                    PersonDbTableData.TABLE_NAME,
                    null,
                    getValues(person),
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private fun getValues(person: Person): ContentValues {
        return PersonDbTableData.personToContentValues(person)
    }

    private fun getPerson(cursor: Cursor): Person {
        return PersonDbTableData.cursorToPerson(cursor)
    }

    @get:JvmName("getAllPersonsProperty")
    val allPersons: ArrayList<Person>
        get() {
            val list = ArrayList<Person>()
            val cursor = readableDatabase.rawQuery(
                "SELECT * FROM " + PersonDbTableData.TABLE_NAME, null
            )
            if (cursor.moveToFirst()) {
                do {
                    list.add(getPerson(cursor))
                } while (cursor.moveToNext())
            }
            cursor.close()
            return list
        }

    fun getAllPersons(): ArrayList<Person> {
        return allPersons
    }

    val personsCount: Int
        get() = DatabaseUtils.queryNumEntries(readableDatabase, PersonDbTableData.TABLE_NAME).toInt()

    fun deletePerson(person: Person): Int {
        return writableDatabase.delete(
            PersonDbTableData.TABLE_NAME,
            PersonDbTableData.COL_ID + " = ?",
            arrayOf(person.id)
        )
    }

    fun deletePerson(id: String): Int {
        return writableDatabase.delete(
            PersonDbTableData.TABLE_NAME,
            PersonDbTableData.COL_ID + " = ?",
            arrayOf(id)
        )
    }

    fun deleteAll() {
        writableDatabase.delete(PersonDbTableData.TABLE_NAME, null, null)
    }

    fun deleteAllPersons(): Int {
        return writableDatabase.delete(PersonDbTableData.TABLE_NAME, null, null)
    }
}
