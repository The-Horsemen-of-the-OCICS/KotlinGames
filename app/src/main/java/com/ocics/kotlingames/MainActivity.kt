package com.ocics.kotlingames

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    fun launchQuiz(v: View) {
        val intent = Intent()
        intent.setClass(this, QuizActivity::class.java)
        startActivity(intent)
    }

    fun launchCount(v: View) {
        val intent = Intent()
        intent.setClass(this, CountActivity::class.java)
        startActivity(intent)
    }

    fun launchScore(v: View) {

    }

    fun exit(v: View) {
        val intent = Intent()
        intent.setClass(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
}