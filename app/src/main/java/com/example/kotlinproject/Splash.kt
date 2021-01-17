package com.example.kotlinproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val handler = android.os.Handler()
            handler.postDelayed({
                val intent = Intent(baseContext, Login::class.java)
                startActivity(intent)
                finish()
            }, 1000)

    }
}