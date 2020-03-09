package com.regondolajosh.sample.cellepathy

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.US
            }
        })

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        swmModeChanger.setOnClickListener {
            audioManager.mode = if (!swmModeChanger.isChecked) AudioManager.STREAM_MUSIC else AudioManager.MODE_NORMAL
            audioManager.isSpeakerphoneOn = !swmModeChanger.isChecked
        }

        btnPlay.setOnClickListener {
            val text = etTextToSound.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
