package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChatMessages;
import com.mantra.checkin.Entities.Models.ChatRoomModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ChatMessagesDbHelper {


    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ChatMessagesDbHelper";

    public static Boolean AddChatMessagesIntoDb(Context context, List<ChatMessages> chatMessages) {
        try {
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<ChatMessages> list = chatMessages;

            for (int i = 0; i < list.size(); i++) {
                ChatMessages chatMessage = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.CHAT_MESSAGES_REFERENCE_FOREIGN_KEY, chatMessage.ChatRoomId);
                values.put(DbTableStrings.CHAT_MESSAGES_ID, chatMessage.MessageId);
                values.put(DbTableStrings.CHAT_MESSAGES_IS_ADMIN_MESSAGE, chatMessage.IsAdminMessage ? "true" : "false");
                values.put(DbTableStrings.CHAT_MESSAGES_IS_IMAGE, chatMessage.IsImage ? "true" : "false");
                if (chatMessage.IsImage) {
                    values.put(DbTableStrings.CHAT_MESSAGES_URL, chatMessage.ImageUrl);
                    values.put(DbTableStrings.CHAT_MESSAGES_MESSAGE, "");
                } else {
                    values.put(DbTableStrings.CHAT_MESSAGES_MESSAGE, chatMessage.Message);
                    values.put(DbTableStrings.CHAT_MESSAGES_URL, "");
                }
                values.put(DbTableStrings.CHAT_MESSAGES_TIME, String.valueOf(chatMessage.TimeStamp));
                values.put(DbTableStrings.CHAT_MESSAGES_SENDER_ID, String.valueOf(chatMessage.SenderId));
                values.put(DbTableStrings.CHAT_MESSAGES_NAME, String.valueOf(chatMessage.SenderName));

                db.insert(DbTableStrings.TABLE_NAME_CHAT_MESSAGES, null, values);
            }
            return true;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static List<ChatMessages> getAllMessagesForChatRoomId(Context context, String chatRoomId) {
        Cursor c = null;
        List<ChatMessages> list = new ArrayList<>();

        try {
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHAT_MESSAGES + " where " + DbTableStrings.CHAT_MESSAGES_REFERENCE_FOREIGN_KEY + " = \"" + chatRoomId + "\"", null);

            if (c.getCount() != 0) {
                if (c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            ChatMessages model = new ChatMessages();

                            model.ChatRoomId = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_REFERENCE_FOREIGN_KEY));
                            model.MessageId = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_ID));
                            model.Message = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_MESSAGE));
                            model.ImageUrl = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_URL));
                            model.SenderName = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_NAME));
                            model.SenderId = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_SENDER_ID));

                            model.IsAdminMessage = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_IS_ADMIN_MESSAGE)).equals("true");
                            model.IsImage = c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_IS_IMAGE)).equals("true");

                            DateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy", Locale.US);
                            model.TimeStamp = formatter.parse(c.getString(c.getColumnIndex(DbTableStrings.CHAT_MESSAGES_TIME)));

                            list.add(model);
                        } while (c.moveToNext());
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return list;
    }
}
