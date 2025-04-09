package com.example.translingoai

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val originalText: String,
    val translatedText: String,
    val timestamp: Long = System.currentTimeMillis()
)
