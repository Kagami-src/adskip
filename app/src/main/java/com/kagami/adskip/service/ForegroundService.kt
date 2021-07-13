package com.kagami.adskip.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kagami.adskip.R
import com.kagami.adskip.ui.main.MainActivity
class ForegroundService: Service() {
    companion object{
        const val CHANNEL_ID= "skipad.foreground"
        const val NOTIFICATION_ID=1
        fun start(context: Context, cmd:String?=null){
            context.startForegroundService(Intent(context, ForegroundService::class.java).apply {
                cmd?.let { putExtra("cmd",it) }
            })

        }
        fun stop(context: Context){
            context.stopService(Intent(context, ForegroundService::class.java))
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        startForeground(ForegroundService.NOTIFICATION_ID, buildNotification())
    }
    private fun buildNotification(): Notification? {
        createNotificationChannel()
       val contentIntent =
            PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)
        return NotificationCompat.Builder(this, ForegroundService.CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.foreground_notification_desc))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(contentIntent)
            .setChannelId(ForegroundService.CHANNEL_ID)
            .setVibrate(LongArray(0))
            .build()
    }
    private fun createNotificationChannel() {
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        val name: CharSequence = getString(R.string.app_name)
        val description = getString(R.string.foreground_notification_desc)
        val channel = NotificationChannel(
            ForegroundService.CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = description
        channel.enableLights(false)
        manager.createNotificationChannel(channel)
    }
}