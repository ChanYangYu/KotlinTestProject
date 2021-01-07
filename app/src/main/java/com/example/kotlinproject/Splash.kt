package com.example.kotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = android.os.Handler()
            handler.postDelayed({
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 5000)
    }
}