package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.SettingsInfo;

/**
 * Created by adithyar on 9/21/2016.
 */
public class SettingsInfoDBHandler {
    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String DEBUG_TAG = "SettingsINFODBHandler";

    public static void InsertSettingsInfo(Context context, SettingsInfo settingsInfo){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbTableStrings.ISLOGGEDIN,settingsInfo.isLoggedIn);

            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            db.insert(DbTableStrings.TABLE_NAME_SETTINGS_INFO,null,contentValues);
            Log.d(DEBUG_TAG,settingsInfo.isLoggedIn);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean CheckLoginStatus(Context context){
        String status;
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_SETTINGS_INFO , null);
        if (c.moveToFirst()) {
                Log.d(DEBUG_TAG,c.getString(c.getColumnIndex(DbTableStrings.ISLOGGEDIN)));
                status = (c.getString(c.getColumnIndex(DbTableStrings.ISLOGGEDIN)));
                Log.d(DEBUG_TAG, status);
                if (status == "true"){
                    Log.d(DEBUG_TAG,"returning true");
                    return true;
                }else
                {
                    return false;
                }
            }
        Log.d(DEBUG_TAG,"returning false");
        return false;
    }
}
