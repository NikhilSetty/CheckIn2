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
import com.mantra.checkin.Entities.SettingsConstants;

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
            contentValues.put(DbTableStrings.TABLE_SETTINGS_KEYS, settingsInfo.Key);
            contentValues.put(DbTableStrings.TABLE_SETTINGS_VALUES, settingsInfo.Value);

            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            db.insert(DbTableStrings.TABLE_NAME_SETTINGS_INFO,null,contentValues);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean CheckLoginStatus(Context context){
        try {
            String status;
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_SETTINGS_INFO + " where " + DbTableStrings.TABLE_SETTINGS_KEYS + " = \"" + SettingsConstants.LoginStatus + "\"", null);
            if (c.moveToFirst()) {
                status = (c.getString(c.getColumnIndex(DbTableStrings.TABLE_SETTINGS_VALUES)));
                Log.d(DEBUG_TAG, status);
                if (status.equals("true")) {
                    Log.d(DEBUG_TAG, "returning true");
                    return true;
                } else {
                    return false;
                }
            }
            Log.d(DEBUG_TAG, "returning false");
        }catch (Exception e){
            Log.e(DEBUG_TAG, e.getMessage());
        }
        return false;
    }
}
