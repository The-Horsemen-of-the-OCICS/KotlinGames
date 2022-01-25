package com.ocics.kotlingames

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.ocics.kotlingames.viewmodel.ScoreViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val mScoreViewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        mScoreViewModel.quizGameScore = sharedPref.getLong(getString(R.string.quiz_score), 0)
        mScoreViewModel.sheepGameScore = sharedPref.getLong(getString(R.string.sheep_score), 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            Log.d(TAG, "shared pref saves: quiz_score=${mScoreViewModel.quizGameScore}, sheep_score=${mScoreViewModel.sheepGameScore}" )
            putLong(getString(R.string.quiz_score), mScoreViewModel.quizGameScore)
            putLong(getString(R.string.sheep_score), mScoreViewModel.sheepGameScore)
            apply()
        }

    }

    fun launchQuiz(v: View) {
        val intent = Intent()
        intent.setClass(this, QuizActivity::class.java)
        startActivity(intent)
    }

    fun launchSheep(v: View) {

    }

    fun launchScore(v: View) {

    }

    fun exit(v: View) {
        finish()
    }
}