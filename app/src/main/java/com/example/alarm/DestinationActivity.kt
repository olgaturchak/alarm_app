package com.example.alarm

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarm.databinding.ActivityDestinationBinding
import com.example.alarm.databinding.ActivityMainBinding

class DestinationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDestinationBinding
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer?.run {
            if (isPlaying) {
                stop()
                release()
            }
            mediaPlayer = null
        }


        binding.setNewAlarm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }


}
