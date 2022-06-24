package com.david.myapplication.background

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.david.myapplication.R

class MusicService : Service(), MediaPlayer.OnCompletionListener {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val musicThread = Thread {
            kotlin.run {
                playMusic()
            }
        }
        musicThread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun playMusic() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.f1theme)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(0.1F, 0.1F)
        mediaPlayer?.setOnCompletionListener(this)
    }

    override fun onCompletion(mp: MediaPlayer?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer = null
    }

    companion object {
        const val NOTIFICATION_ID = 1 //Este ID nunca puede ser 0
        const val NOTIFICATION_ID_CHANNEL = 2
        const val NOTIFICATION_CHANNEL_ID = "MimoService"
        const val channelName = "Media"
    }
}