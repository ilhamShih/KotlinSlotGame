package com.template

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var SPLASH_TIME = 3000
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, BazeActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME.toLong())
    }
}