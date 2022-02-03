package com.ocics.kotlingames

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var mQuizScore: Long = 0
    private var mCountScore: Float = 0f

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
        loadScore()
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("Latest Scores")
            .setMessage("Quiz Score: $mQuizScore\nCount Score: $mCountScore" )
            .setPositiveButton("Ok") { dialog, id ->
                dialog.dismiss()
            }
            .show()

    }

    fun loadScore() {
        val sharedPref = getSharedPreferences("saved_scores", Context.MODE_PRIVATE) ?: return
        mQuizScore = sharedPref.getLong(getString(R.string.quiz_score), 0)
        mCountScore = sharedPref.getFloat(getString(R.string.count_score), 0f)
        Log.d(TAG, "loadScore(), mQuizScore=$mQuizScore, mCountScore=$mCountScore")

    }

    fun exit(v: View) {
        val intent = Intent()
        intent.setClass(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
}