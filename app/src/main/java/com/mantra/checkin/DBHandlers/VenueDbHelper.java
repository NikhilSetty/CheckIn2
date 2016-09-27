package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.ContactModel;
import com.mantra.checkin.Entities.Models.VenueModel;

import java.util.List;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class VenueDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "VenueDbHelper";

    public static Boolean AddVenuesToDb(Context context, List<VenueModel> venues) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<VenueModel> list = venues;

            for(int i = 0; i < list.size(); i++) {
                VenueModel venue = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.VENUE_REFERENCE_FOREIGN_KEY, venue.VenueId);
                values.put(DbTableStrings.VENUE_NAME, venue.VenueName);
                values.put(DbTableStrings.VENUE_lat, String.valueOf(venue.VenueLocation.getLatitude()));
                values.put(DbTableStrings.VENUE_long, String.valueOf(venue.VenueLocation.getLongitude()));

                db.insert(DbTableStrings.TABLE_NAME_VENUE_DETAILS, null, values);
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static VenueModel getVenueForId(Context context, String resourceId) {
        Cursor c = null;
        VenueModel model = new VenueModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_VENUE_DETAILS+ " where " + DbTableStrings.VENUE_REFERENCE_FOREIGN_KEY + " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            model.VenueId = c.getString(c.getColumnIndex(DbTableStrings.VENUE_REFERENCE_FOREIGN_KEY));
                            model.VenueName = c.getString(c.getColumnIndex(DbTableStrings.VENUE_NAME));
                            double lattitude = Double.valueOf(c.getString(c.getColumnIndex(DbTableStrings.VENUE_lat)));
                            double longitude = Double.valueOf(c.getString(c.getColumnIndex(DbTableStrings.VENUE_long)));
                            model.VenueLocation.setLatitude(lattitude);
                            model.VenueLocation.setLongitude(longitude);

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
