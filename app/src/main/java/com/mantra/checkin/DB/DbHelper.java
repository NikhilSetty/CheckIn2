package com.mantra.checkin.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
    public DbHelper(Context context) {
        super(context, DatabaseHelperMeta.DB_NAME, null, DatabaseHelperMeta.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("creating " , "DB");
        db.execSQL(Schema.CREATE_TABLE_USERINFO);
        db.execSQL(Schema.CREATE_TABLE_SETTINGS_INFO);
        db.execSQL(Schema.CREATE_TABLE_CHANNEL);
        db.execSQL(Schema.CREATE_TABLE_NAME_APPLICATIONS);
        db.execSQL(Schema.CREATE_TABLE_NAME_CHAT_MESSAGES);
        db.execSQL(Schema.CREATE_TABLE_NAME_CHAT_ROOMS);
        db.execSQL(Schema.CREATE_TABLE_NAME_CONTACTS);
        db.execSQL(Schema.CREATE_TABLE_NAME_VENUE_DETAILS);
        db.execSQL(Schema.CREATE_TABLE_PROFILE_SETTINGS);
        db.execSQL(Schema.CREATE_TABLE_PROFILES);
        db.execSQL(Schema.CREATE_TABLE_RESOURCES);
        db.execSQL(Schema.CREATE_TABLE_URL);
        Log.v("created " , "DB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
