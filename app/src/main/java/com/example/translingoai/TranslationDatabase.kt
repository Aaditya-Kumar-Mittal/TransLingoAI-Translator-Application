package com.example.translingoai

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TranslationHistory::class], version = 1)
abstract class TranslationDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao

    companion object {
        @Volatile
        private var INSTANCE: TranslationDatabase? = null

        fun getDatabase(context: Context): TranslationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TranslationDatabase::class.java,
                    "translation_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
