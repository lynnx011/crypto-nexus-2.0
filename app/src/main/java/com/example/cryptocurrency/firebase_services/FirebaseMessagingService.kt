package com.example.cryptocurrency.firebase_services
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.cryptocurrency.MainActivity
import com.example.cryptocurrency.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val channelId = "notifications_channel"
private const val channelName = "com.example.cryptocurrency.firebase"
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("notificationToken",token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null){
            generateNotification(message.notification!!.title.toString(),message.notification!!.body.toString())
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String) : RemoteViews{
        val remoteView = RemoteViews("com.example.cryptocurrency", R.layout.firebase_noti_layout)
        remoteView.apply {
            setTextViewText(R.id.noti_title,title)
            setTextViewText(R.id.noti_message,message)
            setImageViewResource(R.id.noti_img,R.drawable.app_logo_2)
            return remoteView
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag", "WrongConstant")
    private fun generateNotification(title: String, message: String){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.app_logo_2)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setPriority(10)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setSound(ringtone)
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val notificationChannel = NotificationChannel(channelId, channelName,
                NotificationManager.IMPORTANCE_MAX
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }
}