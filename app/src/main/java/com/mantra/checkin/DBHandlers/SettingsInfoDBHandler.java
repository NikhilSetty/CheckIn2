package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

    public static void InsertSettingsInfo(Context context, SettingsInfo settingsInfo){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbTableStrings.ISLOGGEDIN,settingsInfo.isLoggedIn);

            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            db.insert(DbTableStrings.TABLE_NAME_SETTINGS_INFO,null,contentValues);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
