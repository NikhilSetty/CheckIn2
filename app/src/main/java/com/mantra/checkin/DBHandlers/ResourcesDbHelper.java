package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Enums.ResourceType;
import com.mantra.checkin.Entities.Models.ApplicationModel;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ResourcesDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ResourcesDbHelper";

    public static Boolean AddResourcesToDb(Context context, List<ChannelResourcesModel> resources) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<ChannelResourcesModel> list = resources;

            for(int i = 0; i < list.size(); i++) {
                ChannelResourcesModel resourcesModel = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.RESOURCES_CHANNEL_ID, resourcesModel.ChannelId);
                values.put(DbTableStrings.RESOURCES_ID, resourcesModel.ResourceId);
                values.put(DbTableStrings.RESOURCES_TYPE, ResourceType.getStringForDb(resourcesModel.ResourceType));

                db.insert(DbTableStrings.TABLE_NAME_RESOURCES, null, values);
            }
            return true;
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static List<ChannelResourcesModel> getAllResourcesForChannel(Context context, String channelId) {
        Cursor c = null;
        List<ChannelResourcesModel> list = new ArrayList<>();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_RESOURCES + " where " + DbTableStrings.RESOURCES_CHANNEL_ID + " = \"" + channelId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            ChannelResourcesModel model = new ChannelResourcesModel();

                            model.ChannelId = c.getString(c.getColumnIndex(DbTableStrings.RESOURCES_CHANNEL_ID));
                            model.ResourceId = c.getString(c.getColumnIndex(DbTableStrings.RESOURCES_ID));
                            model.ResourceType = ResourceType.getFromDbString(c.getString(c.getColumnIndex(DbTableStrings.RESOURCES_TYPE)));

                            list.add(model);
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
        return list;
    }
}
