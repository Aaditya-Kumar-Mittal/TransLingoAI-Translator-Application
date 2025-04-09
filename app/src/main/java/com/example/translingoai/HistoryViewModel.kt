package com.example.translingoai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    val allTranslations: LiveData<List<TranslationHistory>> =
        TranslationDatabase.getDatabase(application).translationDao().getAllTranslations()
}
