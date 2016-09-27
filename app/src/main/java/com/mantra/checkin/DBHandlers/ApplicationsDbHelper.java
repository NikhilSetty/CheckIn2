package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.AppLaunchChecker;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.ApplicationModel;
import com.mantra.checkin.Entities.Models.UrlModel;

import java.util.List;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ApplicationsDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ProfileSettingsDbHelper";

    public static Boolean AddApplicationsToDb(Context context, List<ApplicationModel> applications) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<ApplicationModel> list = applications;

            for(int i = 0; i < list.size(); i++) {
                ApplicationModel application = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.APPLICATION_REFERENCE_FOREIGN_KEY, application.ApplicationId);
                values.put(DbTableStrings.APPLICATION_NAME, application.ApplicationName);
                values.put(DbTableStrings.APPLICATION_STORE_URL, application.ApplicationStoreUrl);

                db.insert(DbTableStrings.TABLE_NAME_APPLICATIONS, null, values);
                return true;
            }

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static ApplicationModel getApplicationForId(Context context, String resourceId) {
        Cursor c = null;
        ApplicationModel model = new ApplicationModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_APPLICATIONS + " where " + DbTableStrings.APPLICATION_REFERENCE_FOREIGN_KEY+ " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            model.ApplicationId= c.getString(c.getColumnIndex(DbTableStrings.APPLICATION_REFERENCE_FOREIGN_KEY));
                            model.ApplicationName = c.getString(c.getColumnIndex(DbTableStrings.APPLICATION_NAME));
                            model.ApplicationStoreUrl = c.getString(c.getColumnIndex(DbTableStrings.APPLICATION_STORE_URL));

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
