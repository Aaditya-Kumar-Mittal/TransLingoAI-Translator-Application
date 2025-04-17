package com.example.translingoai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.translingoai.databinding.ActivityTestButtonScreenBinding

class TestButtonScreen : AppCompatActivity() {

    private lateinit var binding: ActivityTestButtonScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestButtonScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "" // Remove default title

        // Fetch username from SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "User")

        // Set username in toolbar title
        // Create custom TextView
        val titleText = TextView(this)
        titleText.text = "Welcome, $username"
        titleText.setTextColor(ContextCompat.getColor(this, R.color.white))
        titleText.textSize = 20f
        titleText.typeface = ResourcesCompat.getFont(this, R.font.calistoga)  // your font
        titleText.layoutParams = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        // Set custom view
        toolbar.addView(titleText)

        // Card listeners
        findViewById<CardView>(R.id.cardEnglishToHindi).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<CardView>(R.id.cardAnyToHindi).setOnClickListener {
            startActivity(Intent(this, AnyLanguageToHindiTranslation::class.java))
        }

        findViewById<CardView>(R.id.cardVoiceTranslation).setOnClickListener {
            startActivity(Intent(this, VoiceTranslator::class.java))
        }

        findViewById<CardView>(R.id.cardRateUs).setOnClickListener {
            startActivity(Intent(this, TranslingoRateUsScreen::class.java))
        }

        findViewById<CardView>(R.id.cardLogout).setOnClickListener {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, login_activity::class.java))
            finish()
        }
    }
}
