package com.ralemancode.acuario.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ralemancode.acuario.notifications.MyWorker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ralemancode.acuario.R


class NotificationFirebase: FirebaseMessagingService() {
    companion object {
        private const val CHANNEL_ID = "example_channel"
        private const val NOTIFICATION_ID = 0
        private const val TAG = "MyFirebaseMsgService"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val exampleChannel = NotificationChannel(
        CHANNEL_ID,
        "Example",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        // agrega aquí todas las configuraciones que necesites
        enableVibration(true)
        description = "just an example"
    }

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            val titulo = remoteMessage.data.get("title")
            val mensaje = remoteMessage.data.get("message")
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }

            if (titulo!="" && mensaje!="") {
                val manager = ContextCompat
                    .getSystemService(applicationContext, NotificationManager::class.java)!!
                remoteMessage.notification?.run {
                    Log.d("Notifyey", "Message title: ${titulo}")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        manager.createNotificationChannel(exampleChannel)
                    val notification = buildNotification(titulo!!, mensaje!!)
                    manager.notify(NOTIFICATION_ID, notification)
                }

            }
        }
        remoteMessage.notification?.let {
            Log.d("Body Notifyey", "Message Notification Body: ${it.body}")
        }
    }

    private fun buildNotification(title: String, body: String): android.app.Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            //.setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }
}