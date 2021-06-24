package com.template

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyInitialActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_activity)

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)

    }
}