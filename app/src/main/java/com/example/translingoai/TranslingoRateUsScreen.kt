package com.example.translingoai

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.translingoai.databinding.ActivityTranslingoRateUsScreenBinding

class TranslingoRateUsScreen : AppCompatActivity() {

    private lateinit var binding: ActivityTranslingoRateUsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTranslingoRateUsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ratingBar2.setOnRatingBarChangeListener { _, rating, _ ->
            showCustomToast(rating)
        }

    }

    private fun showCustomToast(rating: Float) {
        val inflater = LayoutInflater.from(this)
        val layout = inflater.inflate(R.layout.custom_toast1, null)

        val toastText = layout.findViewById<TextView>(R.id.toastText2)
        toastText.text = "Thanks for rating us!"

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}