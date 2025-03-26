package com.example.translingoai

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class AnyLanguageToHindiTranslation : AppCompatActivity() {

    lateinit var fromLanguage: Spinner
    lateinit var translateText: EditText
    lateinit var translatedText: TextView
    lateinit var translateButton: Button

    private val items = arrayOf("English", "Bengali", "Marathi", "Urdu")
    private var selectedFromLanguage = "English" // Default Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_any_language_to_hindi_translation)

        translateText = findViewById(R.id.textToTranslate2)
        fromLanguage = findViewById(R.id.fromLanguagesList1)
        translatedText = findViewById(R.id.translatedTexttoHindi1)
        translateButton = findViewById(R.id.translateButton2)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        fromLanguage.adapter = adapter

        fromLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFromLanguage = items[position].toLowerCase()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        translateButton.setOnClickListener {
            if (translateText.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter text to translate", Toast.LENGTH_SHORT).show()
            } else {
                translateLanguage()
            }
        }
    }

    private fun translateLanguage() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(selectFrom())
            .setTargetLanguage(TranslateLanguage.HINDI) // Always translate to Hindi
            .build()

        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded(DownloadConditions.Builder().build())
            .addOnSuccessListener {
                translator.translate(translateText.text.toString())
                    .addOnSuccessListener { translatedText.text = it }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Translation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Model download failed. Check internet connection.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectFrom(): String {
        return when (selectedFromLanguage) {
            "english" -> TranslateLanguage.ENGLISH
            "bengali" -> TranslateLanguage.BENGALI
            "marathi" -> TranslateLanguage.MARATHI
            "urdu" -> TranslateLanguage.URDU
            else -> TranslateLanguage.ENGLISH // Default to English
        }
    }
}
