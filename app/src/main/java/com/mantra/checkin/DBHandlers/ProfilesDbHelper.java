package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Enums.ProfileType;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;
import com.mantra.checkin.Entities.Models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/26/2016.
 */
public class ProfilesDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ProfilesDbHelper";

    public static Boolean AddProfileToDbIfItDoesNotExist(Context context, List<ProfileModel> list){
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            for(int i = 0; i < list.size(); i++) {
                ProfileModel model = list.get(i);
                Boolean doesProfileExist = DoesProfileExistInDb(context, model.ProfileId);
                if (!doesProfileExist) {
                    ContentValues values = new ContentValues();
                    values.put(DbTableStrings.PROFILES_REFERENCE_FOREIGN_KEY, model.ProfileId);
                    values.put(DbTableStrings.PROFILES_TYPE, ProfileType.getStringForDb(model.ProfileType));

                    ProfileSettingsDbHelper.InsertSettingsIntoProfileSettings(context, model);
                    db.insert(DbTableStrings.TABLE_NAME_PROFILES, null, values);
                } else {
                    // todo add update code
                }
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }


    public static Boolean DoesProfileExistInDb(Context context, String profileId){
        Cursor c = null;
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_PROFILES + " where " + DbTableStrings.PROFILES_REFERENCE_FOREIGN_KEY + " = \"" + profileId + "\"", null);

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

    public static ProfileModel GetProfileForId(Context context, String resourceId) {
        Cursor c = null;
        ProfileModel model = new ProfileModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_PROFILES + " where " + DbTableStrings.PROFILES_REFERENCE_FOREIGN_KEY + " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            model.ProfileId = c.getString(c.getColumnIndex(DbTableStrings.PROFILES_REFERENCE_FOREIGN_KEY));
                            model.ProfileType = ProfileType.getFromDbString(c.getString(c.getColumnIndex(DbTableStrings.PROFILES_TYPE)));

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

        model.settings =  ProfileSettingsDbHelper.getAllSettingsForProfileId(context, model.ProfileId);
        return model;
    }
}
