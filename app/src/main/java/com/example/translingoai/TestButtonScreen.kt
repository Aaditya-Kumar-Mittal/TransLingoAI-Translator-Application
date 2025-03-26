package com.example.translingoai

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.translingoai.databinding.ActivityTestButtonScreenBinding

class TestButtonScreen : AppCompatActivity() {

    private lateinit var binding: ActivityTestButtonScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestButtonScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onlyEnglishToHindi1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.anyToHindi1.setOnClickListener {
            val intent = Intent(this, AnyLanguageToHindiTranslation::class.java)
            startActivity(intent)
        }
    }
}