package com.example.translingoai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.translingoai.databinding.ActivityCustomSplashScreen1Binding
import com.google.android.material.progressindicator.CircularProgressIndicator

class CustomSplashScreen1 : AppCompatActivity() {

    private lateinit var binding: ActivityCustomSplashScreen1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomSplashScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make the splash screen truly full screen (hide status + nav bars)
        window.decorView.systemUiVisibility = (
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        binding.progressBar1.visibility = CircularProgressIndicator.VISIBLE

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        Handler(Looper.getMainLooper()).postDelayed({
            val nextActivity = if (isLoggedIn) {
                TestButtonScreen::class.java
            } else {
                login_activity::class.java
            }
            startActivity(Intent(this, nextActivity))
            finish()
        }, 7000) // Optional delay
    }
}
