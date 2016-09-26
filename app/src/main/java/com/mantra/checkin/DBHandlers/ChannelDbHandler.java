package com.mantra.checkin.DBHandlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChannelDbHandler {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ChannelDbHandler";



    public static Boolean CheckIfUserHasSubscribedToAnyChannels(Context context){
        Cursor c = null;
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHANNEL, null);

            if (c.moveToFirst()) {
                int id = c.getInt(c.getColumnIndex("_id"));
                if(id > 0){
                    return true;
                }
            }
            else{
                return false;
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if(c != null) {
                c.close();
            }
        }
        return false;
    }
}
