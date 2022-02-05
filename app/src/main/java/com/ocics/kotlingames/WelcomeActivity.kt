package com.ocics.kotlingames

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        mediaPlayer = MediaPlayer.create(baseContext, R.raw.music)
        mediaPlayer.isLooping = true
    }

    override fun onResume() {
        super.onResume()
        // Play background music
        if (!mediaPlayer.isPlaying) mediaPlayer.start()

        // Hide system bars
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }


    fun onOkClicked(v: View) {
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
    }
}