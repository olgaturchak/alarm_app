package com.example.alarm

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class AlarmReceiver : BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null // Initialized field for proper release

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "YourApp:WakeLock"
        )
        wakeLock.acquire()
        wakeLock.release()

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, alarmSound)
            mediaPlayer?.isLooping = true // Ensure looping
        }
        mediaPlayer?.start()

        val dismissIntent = Intent(context, DestinationActivity::class.java)
        dismissIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val dismissPendingIntent = PendingIntent.getActivity(context, 0, dismissIntent, PendingIntent.FLAG_IMMUTABLE)



        val notificationBuilder = NotificationCompat.Builder(context!!, "foxandroid")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Alarm") // Clearer title
            .setContentText("Tap to dismiss") // Informative text
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(dismissPendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "POST_NOTIFICATIONS permission is required", Toast.LENGTH_SHORT).show()
            return
        }
        notificationManager.notify(123, notificationBuilder.build())
    }
}
