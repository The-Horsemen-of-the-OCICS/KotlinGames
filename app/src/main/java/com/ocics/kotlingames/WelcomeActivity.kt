package com.ocics.kotlingames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    fun onOkClicked(v: View) {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }
}