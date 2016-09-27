package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.ApplicationModel;
import com.mantra.checkin.Entities.Models.ContactModel;

import java.util.List;

/**
 * Created by nravishankar on 9/27/2016.
 */
public class ContactsDbHelper {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ContactsDbHelper";

    public static Boolean AddContactsToDb(Context context, List<ContactModel> contacts) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            List<ContactModel> list = contacts;

            for(int i = 0; i < list.size(); i++) {
                ContactModel contact = list.get(i);

                ContentValues values = new ContentValues();
                values.put(DbTableStrings.CONTACTS_REFERENCE_FOREIGN_KEY, contact.ContactId);
                values.put(DbTableStrings.CONTACT_NAME, contact.ContactName);
                values.put(DbTableStrings.CONTACT_NUMBER, contact.ContactNumber);
                values.put(DbTableStrings.CONTACT_POSITION, contact.ContactPosition);

                db.insert(DbTableStrings.TABLE_NAME_CONTACTS, null, values);
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static ContactModel getContactForId(Context context, String resourceId) {
        Cursor c = null;
        ContactModel model = new ContactModel();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CONTACTS + " where " + DbTableStrings.CONTACTS_REFERENCE_FOREIGN_KEY + " = \"" + resourceId + "\"", null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            model.ContactId = c.getString(c.getColumnIndex(DbTableStrings.CONTACTS_REFERENCE_FOREIGN_KEY));
                            model.ContactPosition = c.getString(c.getColumnIndex(DbTableStrings.CONTACT_POSITION));
                            model.ContactNumber = c.getString(c.getColumnIndex(DbTableStrings.CONTACT_NUMBER));
                            model.ContactName = c.getString(c.getColumnIndex(DbTableStrings.CONTACT_NAME));

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
