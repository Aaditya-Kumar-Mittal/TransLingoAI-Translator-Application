package com.example.translingoai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class VoiceTranslator : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var fromLanguage: Spinner
    private lateinit var toLanguage: Spinner
    private lateinit var translateText: EditText
    private lateinit var translatedText: TextView
    private lateinit var translateButton: Button
    private lateinit var speakButton: Button
    private lateinit var tts: TextToSpeech

    private val languages = mapOf(
        "English" to TranslateLanguage.ENGLISH,
        "Hindi" to TranslateLanguage.HINDI,
        "Bengali" to TranslateLanguage.BENGALI,
        "Marathi" to TranslateLanguage.MARATHI,
        "Urdu" to TranslateLanguage.URDU
    )

    private var selectedFromLanguage = TranslateLanguage.ENGLISH
    private var selectedToLanguage = TranslateLanguage.HINDI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_translator)

        translateText = findViewById(R.id.textToTranslate)
        fromLanguage = findViewById(R.id.fromLanguages)
        toLanguage = findViewById(R.id.toLanguages)
        translatedText = findViewById(R.id.translatedText)
        translateButton = findViewById(R.id.translateButton)
        speakButton = findViewById(R.id.speakButton)

        tts = TextToSpeech(this, this)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages.keys.toList())
        fromLanguage.adapter = adapter
        toLanguage.adapter = adapter

        fromLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFromLanguage = languages[adapter.getItem(position)] ?: TranslateLanguage.ENGLISH
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        toLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedToLanguage = languages[adapter.getItem(position)] ?: TranslateLanguage.HINDI
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

        speakButton.setOnClickListener {
            startSpeechRecognition()
        }
    }

    private fun translateLanguage() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(selectedFromLanguage)
            .setTargetLanguage(selectedToLanguage)
            .build()

        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded(DownloadConditions.Builder().build())
            .addOnSuccessListener {
                translator.translate(translateText.text.toString())
                    .addOnSuccessListener { translated ->
                        translatedText.text = translated
                        speakText(translated)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Translation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Model download failed. Check internet connection.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")

        try {
            startActivityForResult(intent, 1)
        } catch (e: Exception) {
            Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            translateText.setText(result?.get(0))
        }
    }

    private fun speakText(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.forLanguageTag(selectedToLanguage)
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
