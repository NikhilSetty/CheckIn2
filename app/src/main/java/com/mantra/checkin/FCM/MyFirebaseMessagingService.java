package com.mantra.checkin.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    String type;
    int intType;

    String chatSenderName;
    String chatMessage;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        type = remoteMessage.getData().get("Type");
        intType = Integer.parseInt(type);

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification mBuilder =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setContentTitle("Chat Notification")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(chatSenderName + ":" + chatMessage))
                            .setContentText(chatSenderName + ":" + chatMessage)
                            .setAutoCancel(true)
                            .build();


            mNotificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder);
    }
}
