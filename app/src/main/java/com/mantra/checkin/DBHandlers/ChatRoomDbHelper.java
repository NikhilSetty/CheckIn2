package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Enums.ProfileType;
import com.mantra.checkin.Entities.Models.ChatRoomModel;
import com.mantra.checkin.Entities.Models.ProfileModel;
import com.mantra.checkin.Entities.Models.VenueModel;

import java.util.List;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ChatRoomDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ChatRoomDbHelper";

    public static Boolean AddChatRoomsToDb(Context context, List<ChatRoomModel> chatRooms) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<ChatRoomModel> list = chatRooms;

            for(int i = 0; i < list.size(); i++) {
                ChatRoomModel chatRoom = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.CHAT_ROOMS_REFERENCE_FOREIGN_KEY, chatRoom.ChatRoomId);
                values.put(DbTableStrings.CHAT_ROOMS_NAME, chatRoom.ChatRoomName);

                ChatMessagesDbHelper.AddChatMessagesIntoDb(context, chatRoom.messages);

                db.insert(DbTableStrings.TABLE_NAME_CHAT_ROOMS, null, values);
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static ChatRoomModel getChatRoomForId(Context context, String resourceId) {
        Cursor c = null;
        ChatRoomModel model = new ChatRoomModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHAT_ROOMS + " where " + DbTableStrings.CHAT_ROOMS_REFERENCE_FOREIGN_KEY  + " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {

                            model.ChatRoomId = c.getString(c.getColumnIndex(DbTableStrings.CHAT_ROOMS_REFERENCE_FOREIGN_KEY));
                            model.ChatRoomName = c.getString(c.getColumnIndex(DbTableStrings.CHAT_ROOMS_NAME));

                        } while (c.moveToNext());
                    }
                }
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if(c != null) {
                c.close();
            }
        }

        model.messages =  ChatMessagesDbHelper.getAllMessagesForChatRoomId(context, model.ChatRoomId);
        return model;
    }
}
