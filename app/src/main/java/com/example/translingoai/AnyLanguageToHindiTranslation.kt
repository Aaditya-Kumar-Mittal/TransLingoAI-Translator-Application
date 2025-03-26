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
    lateinit var toLanguage: Spinner
    lateinit var translateText: EditText
    lateinit var translatedText1: TextView
    lateinit var translateButton: Button

    private val items = arrayOf("English", "Hindi", "Bengali", "Marathi", "Urdu")
    private var selectedFromLanguage = ""
    private var selectedToLanguage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_any_language_to_hindi_translation)

        translateText = findViewById(R.id.textToTranslate)
        fromLanguage = findViewById(R.id.fromLanguages)
        toLanguage = findViewById(R.id.toLanguages)
        translatedText1 = findViewById(R.id.translatedText)
        translateButton = findViewById(R.id.translateButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        fromLanguage.adapter = adapter
        toLanguage.adapter = adapter

        translateButton.setOnClickListener {
            if (translateText.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter text to translate", Toast.LENGTH_SHORT).show()
            } else if (selectedFromLanguage == selectedToLanguage) {
                Toast.makeText(this, "Choose different languages!", Toast.LENGTH_SHORT).show()
            } else {
                translateLanguage()
            }
        }

        fromLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFromLanguage = items[position].toLowerCase()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        toLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedToLanguage = items[position].toLowerCase()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun translateLanguage() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(selectFrom())
            .setTargetLanguage(selectTo())
            .build()
        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded().addOnSuccessListener {
            translator.translate(translateText.text.toString()).addOnSuccessListener {
                translatedText1.text = it
            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectFrom(): String {
        return when (selectedFromLanguage) {
            "english" -> TranslateLanguage.ENGLISH
            "hindi" -> TranslateLanguage.HINDI
            "bengali" -> TranslateLanguage.BENGALI
            "marathi" -> TranslateLanguage.MARATHI
            "urdu" -> TranslateLanguage.URDU
            else -> TranslateLanguage.ENGLISH // Default to English
        }
    }

    private fun selectTo(): String {
        return when (selectedToLanguage) {
            "english" -> TranslateLanguage.ENGLISH
            "hindi" -> TranslateLanguage.HINDI
            "bengali" -> TranslateLanguage.BENGALI
            "marathi" -> TranslateLanguage.MARATHI
            "urdu" -> TranslateLanguage.URDU
            else -> TranslateLanguage.HINDI // Default to Hindi
        }
    }
}
