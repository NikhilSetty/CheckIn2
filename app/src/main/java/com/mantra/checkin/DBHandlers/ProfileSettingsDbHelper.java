package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Enums.ProfileType;
import com.mantra.checkin.Entities.Models.ProfileModel;
import com.mantra.checkin.Entities.Models.ProfileSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ProfileSettingsDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ProfileSettingsDbHelper";

    public static Boolean InsertSettingsIntoProfileSettings(Context context, ProfileModel model) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            Map<String, String> map = model.settings;
            Object[] keys = map.keySet().toArray();

            for(int i = 0; i < map.size(); i++) {
                ProfileSettings modelProfileSettings = new ProfileSettings();
                modelProfileSettings.ProfileId = model.ProfileId;
                modelProfileSettings.ProfileSettingKey = keys[i].toString();
                modelProfileSettings.ProfileSettingValue = map.get(keys[i].toString());

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.PROFILE_ID_FOREIGN_KEY, model.ProfileId);
                values.put(DbTableStrings.PROFILE_SETTINGS_KEY, modelProfileSettings.ProfileSettingKey);
                values.put(DbTableStrings.PROFILE_SETTINGS_VALUES, modelProfileSettings.ProfileSettingValue);

                db.insert(DbTableStrings.TABLE_NAME_PROFILES_SETTINGS, null, values);
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static Map<String, String> getAllSettingsForProfileId(Context context, String profileId) {
        Cursor c = null;
        Map<String, String> map = new HashMap<>();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_PROFILES_SETTINGS + " where " + DbTableStrings.PROFILE_ID_FOREIGN_KEY + " = \"" + profileId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            map.put(c.getString(c.getColumnIndex(DbTableStrings.PROFILE_SETTINGS_KEY)), c.getString(c.getColumnIndex(DbTableStrings.PROFILE_SETTINGS_VALUES)));
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

        return map;
    }
}
