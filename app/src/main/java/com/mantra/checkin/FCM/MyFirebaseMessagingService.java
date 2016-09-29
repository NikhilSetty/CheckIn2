package com.mantra.checkin.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mantra.checkin.DBHandlers.ChatMessagesDbHelper;
import com.mantra.checkin.Entities.JSONKEYS.SendMessageJsonKeys;
import com.mantra.checkin.Entities.Models.ChatMessages;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.R;
import com.mantra.checkin.UiFragments.ChatRooms.ChatRoomFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    String type;
    int intType;

    String chatSenderName;
    String chatMessage;

    private static final String TAG = "MyFirebaseMsgService";
/*
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
    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));

        sendNotification(remoteMessage.getData());

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(Map<String, String> map) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        try{
            ChatMessages model = new ChatMessages();
            model.SenderId = map.get(SendMessageJsonKeys.UserId);
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            model.TimeStamp = formatter.parse(map.get(SendMessageJsonKeys.TimeOfGeneration));
            model.SenderName = map.get(SendMessageJsonKeys.UserName);
            model.IsAdminMessage= map.get(SendMessageJsonKeys.IsAdminMessage).equals("true");
            model.IsImage = map.get(SendMessageJsonKeys.IsImage).equals("true");
            if(model.IsImage){
                model.ImageUrl = map.get(SendMessageJsonKeys.ImageArray);
            }else {
                model.Message = map.get(SendMessageJsonKeys.Message);
            }

            List<ChatMessages> list = new ArrayList<>();
            list.add(model);

            Boolean isSuccess = ChatMessagesDbHelper.AddChatMessagesIntoDb(getApplicationContext(), list);
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Chat Message Recieved!")
                    .setContentText("From " + model.SenderName);
            NotificationManager manager = (NotificationManager)     getSystemService(NOTIFICATION_SERVICE);
            if(MainActivity.isActivityVisible()){
                MainActivity.getChatFragment();
            }else {
                manager.notify(0, builder.build());
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }
}
