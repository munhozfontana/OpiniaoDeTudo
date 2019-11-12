package com.example.opiniaodetudo.infra.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.extension.SQLiteDatabaseTool
import java.util.*

class ReviewDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "review_database"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${ReviewTableInfo.TABLE_NAME} (" +
                    " ${ReviewTableInfo.COLUMN_ID} TEXT PRIMARY KEY, " +
                    " ${ReviewTableInfo.COLUMN_NAME} TEXT NOT NULL, " +
                    " ${ReviewTableInfo.COLUMN_REVIEW} TEXT " +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
}








