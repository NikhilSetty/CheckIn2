package com.mantra.checkin.DBHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.mantra.checkin.DB.DbHelper;
import com.mantra.checkin.DB.DbTableStrings;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.ChannelResourcesModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChannelDbHandler {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;
    private static String TAG = "ChannelDbHandler";



    public static Boolean CheckIfUserHasSubscribedToAnyChannels(Context context){
        Cursor c = null;
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHANNEL, null);

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

    public static Boolean AddChannelToDbIfItDoesNotExist(Context context, ChannelModel model){
        Cursor c = null;
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            Boolean doesChannelExist = DoesChannelExistInDb(context, model.ChannelId);
            if(!doesChannelExist) {
                ContentValues values = new ContentValues();
                values.put(DbTableStrings.CHANNEL_ID, model.ChannelId);
                values.put(DbTableStrings.CHANNEL_IS_ACTIVE, "true");
                values.put(DbTableStrings.CHANNEL_IS_LOCATION_BASED, model.IsLocationBased ? "true" : "false");
                values.put(DbTableStrings.CHANNEL_ISPUBLIC, model.IsPublic ? "true" : "false");
                values.put(DbTableStrings.CHANNEL_LATTITUDE, String.valueOf(model.ChannelActiveLocation.getLatitude()));
                values.put(DbTableStrings.CHANNEL_LONGTITUDE, String.valueOf(model.ChannelActiveLocation.getLongitude()));
                values.put(DbTableStrings.CHANNEL_TIME_OF_START, String.valueOf(model.ChannelStartDate));
                values.put(DbTableStrings.CHANNEL_TIME_OF_END, String.valueOf(model.ChannelEndDateTime));


                Boolean  resourcesInsertionSuccessfull = ResourcesDbHelper.AddResourcesToDb(context, model.Resources);
                Boolean profileInsertionSuccessfull = ProfilesDbHelper.AddProfileToDbIfItDoesNotExist(context, model.Profiles);
                Boolean urlsInsertionSuccessfull = UrlsDbHelper.AddUrlsToDb(context, model.Urls);
                Boolean applicationsInsertionToDbSuccessfull = ApplicationsDbHelper.AddApplicationsToDb(context, model.Applications);
                Boolean contactsInsertionToDbSuccessfull = ContactsDbHelper.AddContactsToDb(context, model.Contacts);
                Boolean venueInsertionToDbSuccessfull = VenueDbHelper.AddVenuesToDb(context, model.Venues);
                Boolean chatRoomsInsertionToDbSuccessfull = ChatRoomDbHelper.AddChatRoomsToDb(context, model.ChatRooms);

                db.insert(DbTableStrings.TABLE_NAME_CHANNEL, null, values);

            }else {
                // todo add update code
            }
            return true;

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if(c != null) {
                c.close();
            }
        }
        return false;
    }

    public static Boolean DoesChannelExistInDb(Context context, String channelId){
        Cursor c = null;
        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHANNEL + " where " + DbTableStrings.CHANNEL_ID + " = \"" + channelId + "\"", null);

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

    public static List<ChannelModel> getAllChannelsAndDetails(Context context) {
        Cursor c = null;
        List<ChannelModel> list = new ArrayList<>();

        try{
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

            c = db.rawQuery("Select * from " + DbTableStrings.TABLE_NAME_CHANNEL, null);

            if (c.getCount() != 0) {
                if(c.getCount() != -1) {
                    if (c.moveToFirst()) {
                        do {
                            ChannelModel model = new ChannelModel();

                            model.ChannelId = c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_ID));
                            model.Name = c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_NAME));
                            model.IsPublic = (c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_ISPUBLIC))).equals("true");
                            model.IsLocationBased = (c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_IS_LOCATION_BASED))).equals("true");
                            double lattitude = Double.valueOf(c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_LATTITUDE)));
                            double longitude = Double.valueOf(c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_LONGTITUDE)));
                            model.ChannelActiveLocation.setLatitude(lattitude);
                            model.ChannelActiveLocation.setLongitude(longitude);
                            DateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy", Locale.US);
                            model.ChannelStartDate = formatter.parse(c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_TIME_OF_START)));
                            model.ChannelEndDateTime = formatter.parse(c.getString(c.getColumnIndex(DbTableStrings.CHANNEL_TIME_OF_END)));

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

        for(int i = 0; i < list.size(); i++){
            ChannelModel model = list.get(i);

            model.Resources = ResourcesDbHelper.getAllResourcesForChannel(context, model.ChannelId);
            for(int j = 0; j < model.Resources.size(); j++){
                ChannelResourcesModel resourcesModel = model.Resources.get(j);
                switch (resourcesModel.ResourceType){
                    case Profiles:
                        model.Profiles.add(ProfilesDbHelper.GetProfileForId(context, resourcesModel.ResourceId));
                        break;
                    case Url:
                        model.Urls.add(UrlsDbHelper.GetUrlForId(context, resourcesModel.ResourceId));
                        break;
                    case Application:
                        model.Applications.add(ApplicationsDbHelper.getApplicationForId(context, resourcesModel.ResourceId));
                        break;
                    case Contact:
                        model.Contacts.add(ContactsDbHelper.getContactForId(context, resourcesModel.ResourceId));
                        break;
                    case Venue:
                        model.Venues.add(VenueDbHelper.getVenueForId(context, resourcesModel.ResourceId));
                        break;
                    case ChatRoom:
                        model.ChatRooms.add(ChatRoomDbHelper.getChatRoomForId(context, resourcesModel.ResourceId));
                        break;
                }
            }
        }
        return list;
    }
}
