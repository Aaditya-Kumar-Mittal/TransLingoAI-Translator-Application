package com.example.translingoai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var englishHindiTranslator: Translator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val translateButton = findViewById<Button>(R.id.translateButton)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val historyButton = findViewById<Button>(R.id.historyButton)

        ratingBar.visibility = View.GONE

        // Create translator
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        englishHindiTranslator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder().requireWifi().build()

        englishHindiTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Enable translation
                translateButton.setOnClickListener {
                    val textToTranslate = inputText.text.toString().trim()
                    if (textToTranslate.isNotEmpty()) {
                        translateText(textToTranslate, outputText, ratingBar)
                    } else {
                        Toast.makeText(this, "Please enter some text.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Download Error", exception.toString())
                outputText.text = "Download Error!"
            }

        // RatingBar listener
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(
                this,
                "You rated this translation: $rating stars",
                Toast.LENGTH_SHORT
            ).show()
        }

        // History Button listener
        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun translateText(inputText: String, outputText: TextView, ratingBar: RatingBar) {
        englishHindiTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                outputText.text = translatedText
                ratingBar.visibility = View.VISIBLE

                // Save to database
                val db = TranslationDatabase.getDatabase(applicationContext)
                val historyItem = TranslationHistory(
                    originalText = inputText,
                    translatedText = translatedText
                )
                lifecycleScope.launch {
                    db.translationDao().insert(historyItem)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Translation Error", exception.toString())
                outputText.text = "Translation Error!"
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        englishHindiTranslator.close()
    }
}

