package com.example.translingoai

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private lateinit var englishHindiTranslator: Translator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val outputText = findViewById<TextView>(R.id.outputText)
        val translateButton = findViewById<Button>(R.id.translateButton)

        // Create an English-Hindi translator:
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        englishHindiTranslator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        englishHindiTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
                // Translate the text using separate function
                translateButton.setOnClickListener {
                    translateText(inputText.text.toString(), outputText)
                }
            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                // ...
                Log.e("Download Error", exception.toString())
                outputText.text = "Download Error!"
            }
    }

    private fun translateText(inputText: String, outputText: TextView) {
        englishHindiTranslator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                // Translation successful.
                outputText.text = translatedText
            }
            .addOnFailureListener { exception ->
                // Error.
                // ...
                Log.e("Translation Error", exception.toString())
                outputText.text = "Translation Error!"
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        englishHindiTranslator.close()
    }
}