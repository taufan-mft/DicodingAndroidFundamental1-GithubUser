package com.topanlabs.githubuser.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.topanlabs.githubuser.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(
            context, title = context.getString(R.string.check_out), message = context.getString(
                R.string.something_new
            ), notifId = 100
        )
    }

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, context.getString(R.string.reminder_on_toast), Toast.LENGTH_SHORT)
            .show()
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.getString(R.string.reminder_off_toast), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String,
        notifId: Int
    ) {
        val channelId = "Channel_1"
        val channelName = "Reminder"
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

}