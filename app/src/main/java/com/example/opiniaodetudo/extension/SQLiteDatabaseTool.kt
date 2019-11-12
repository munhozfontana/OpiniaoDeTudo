package com.example.opiniaodetudo.extension

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class SQLiteDatabaseTool{
    companion object {
        fun selectAll(db:SQLiteDatabase, tableName:String, columns:Array<String>): Cursor {
            return db.query(
                tableName,
                columns,
                null,
                null,
                null,
                null,
                null
            )
        }
    }
}
