package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.UserInfo;

/**
 * Created by adithyar on 9/21/2016.
 */
public class UserInfoDBHandler {

    private static final String TAG = "UserInfoDBHandler";

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;

    public static void InsertUserDetails(Context context, UserInfo userinfo){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbTableStrings.USERNAME,userinfo.UserName);
            contentValues.put(DbTableStrings.USEREMAIL,userinfo.UserEmail);
            contentValues.put(DbTableStrings.USERPHOTO,userinfo.UserPhoto);
            contentValues.put(DbTableStrings.FIRSTNAME,userinfo.FirstName);
            contentValues.put(DbTableStrings.LASTNAME,userinfo.LastName);
            contentValues.put(DbTableStrings.USERID,userinfo.UserID);
            contentValues.put(DbTableStrings.USER_PHONE_NUMBER, userinfo.PhoneNumber);

            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            db.insert(DbTableStrings.TABLE_NAME_USER_INFO,null,contentValues);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean CheckIfUserExistsInDB(Context context){

        try {
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            Cursor c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_USER_INFO, null);
            if (c.moveToFirst()) {
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static UserInfo FetchCurrentUserDetails(Context context) {
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            UserInfo userModel = new UserInfo();

            Cursor c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_USER_INFO , null);
            if (c.moveToFirst()) {
                userModel.setFirstName(c.getString(c.getColumnIndex(DbTableStrings.FIRSTNAME)));
                userModel.setLastName(c.getString(c.getColumnIndex(DbTableStrings.LASTNAME)));
                userModel.setUserEmail(c.getString(c.getColumnIndex(DbTableStrings.USEREMAIL)));
                userModel.setUserID(c.getString(c.getColumnIndex(DbTableStrings.USERID)));
                userModel.setUserName(c.getString(c.getColumnIndex(DbTableStrings.USERNAME)));
                userModel.setPhoneNumber(c.getString(c.getColumnIndex(DbTableStrings.USER_PHONE_NUMBER)));
                userModel.setCheckInServerUserId(c.getString(c.getColumnIndex(DbTableStrings.CHECKIN_SERVER_USERID)));
                userModel.setUserPhoto(c.getString(c.getColumnIndex(DbTableStrings.USERPHOTO)));
                userModel._id = c.getInt(c.getColumnIndex("_id"));

                return  userModel;
            }
            else{
                return null;
            }

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static boolean updatePhoneNumberForUser(Context context, String phoneNumber){
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            UserInfo oldUserInfo = FetchCurrentUserDetails(context);

            if(oldUserInfo != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbTableStrings.USER_PHONE_NUMBER, phoneNumber);

                db.update(DbTableStrings.TABLE_NAME_USER_INFO, contentValues, "_id =" + oldUserInfo._id, null);

                return true;
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }
    public static void InsertCheckinServerUserID(Context context,String userid){
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            UserInfo dbuser = FetchCurrentUserDetails(context);
            if (dbuser !=null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbTableStrings.CHECKIN_SERVER_USERID, userid);
                db.update(DbTableStrings.TABLE_NAME_USER_INFO, contentValues, "_id =" + dbuser._id, null);
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }
}
