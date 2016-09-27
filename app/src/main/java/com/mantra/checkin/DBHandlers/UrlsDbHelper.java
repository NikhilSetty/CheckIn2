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
import com.mantra.checkin.Entities.Models.UrlModel;

import java.util.List;
import java.util.Map;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class UrlsDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ProfileSettingsDbHelper";

    public static Boolean AddUrlsToDb(Context context, List<UrlModel> urls) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<UrlModel> list = urls;

            for(int i = 0; i < urls.size(); i++) {
                UrlModel url = urls.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.URL_ID, url.UrlId);
                values.put(DbTableStrings.URL_ADDRESS, url.UrlAddress);
                values.put(DbTableStrings.URL_ICON_URL, url.UrlIconAddress);
                values.put(DbTableStrings.URL_NAME, url.UrlName);
                values.put(DbTableStrings.URL_OPEN_IN_BROWSER, url.OpenInBrowser ? "true" : "false");

                db.insert(DbTableStrings.TABLE_NAME_URL, null, values);
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static UrlModel GetUrlForId(Context context, String resourceId) {
        Cursor c = null;
        UrlModel model = new UrlModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_URL + " where " + DbTableStrings.URL_ID + " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {

                            model.UrlId = c.getString(c.getColumnIndex(DbTableStrings.URL_ID));
                            model.UrlAddress = c.getString(c.getColumnIndex(DbTableStrings.URL_ADDRESS));
                            model.UrlIconAddress = c.getString(c.getColumnIndex(DbTableStrings.URL_ICON_URL));
                            model.UrlName = c.getString(c.getColumnIndex(DbTableStrings.URL_NAME));

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
        return model;
    }
}
