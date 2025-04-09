package com.example.translingoai

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(translation: TranslationHistory)

    @Query("SELECT * FROM translations ORDER BY timestamp DESC")
    fun getAllTranslations(): LiveData<List<TranslationHistory>>

    @Query("DELETE FROM translations")
    suspend fun deleteAll()
}
