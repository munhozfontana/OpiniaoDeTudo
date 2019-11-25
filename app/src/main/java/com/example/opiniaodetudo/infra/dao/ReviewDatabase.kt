package com.example.opiniaodetudo.infra.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.opiniaodetudo.domain.Review

@Database(entities = arrayOf(Review::class), version = 2)
abstract class ReviewDatabase : RoomDatabase() {
    companion object {


        private var instance: ReviewDatabase? = null
        fun getInstance(context: Context): ReviewDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, ReviewDatabase::class.java, "review_database")
                        .addMigrations(migration_1_2).fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build()
            }
            return instance!!
        }


        private var migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE ${ReviewTableInfo.TABLE_NAME} "
                            + "ADD COLUMN ${ReviewTableInfo.COLUMN_PHOTO_PATH} TEXT"
                )
                database.execSQL(
                    "ALTER TABLE ${ReviewTableInfo.TABLE_NAME} " +
                            "ADD COLUMN ${ReviewTableInfo.COLUMN_THUMBNAIL} BLOB"
                )
            }
        }

    }

    abstract fun reviewDao(): ReviewDao

}

