package com.mantra.checkin.Entities.Models;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.mantra.checkin.DBHandlers.ChannelDbHandler;
import com.mantra.checkin.Entities.Enums.ResourceType;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChannelModel {

    private final static String TAG = "ChannelModel";


    public String ChannelId = "";
    public String Name = "";
    public Boolean IsPublic = true;
    public Boolean IsLocationBased = false;
    public Location ChannelActiveLocation = new Location("");
    public Date ChannelStartDate = new Date();
    public Date ChannelEndDateTime = new Date();

    public List<ChannelResourcesModel> Resources = new ArrayList<>();

    public List<ProfileModel> Profiles = new ArrayList<ProfileModel>();
    public List<UrlModel> Urls = new ArrayList<UrlModel>();
    public List<ApplicationModel> Applications = new ArrayList<ApplicationModel>();
    public List<ContactModel> Contacts = new ArrayList<ContactModel>();
    public List<VenueModel> Venues = new ArrayList<VenueModel>();
    public List<ChatRoomModel> ChatRooms = new ArrayList<ChatRoomModel>();


    public ChannelModel(){
        ChannelActiveLocation.setLatitude(0);
        ChannelActiveLocation.setLongitude(0);
    }

    public static ChannelModel addChannelToDbAndGetModelFromJson(Context context, String jsonEntity){
        try{

            JSONObject root = new JSONObject(jsonEntity);

            // Channel Objects
            ChannelModel model = new ChannelModel();
            model.ChannelId = Integer.toString(root.getInt(ChannelJsonKeys.ChannelId));
            model.Name = root.getString(ChannelJsonKeys.ChannelName);
            model.IsPublic = root.getBoolean(ChannelJsonKeys.ChannelIsPublic);
            model.IsLocationBased = root.getBoolean(ChannelJsonKeys.ChannelIsLocationBased);

            JSONArray locationArray = root.getJSONArray(ChannelJsonKeys.ChannelCoOrdinatesArray);
            for(int i = 0; i < locationArray.length(); i++){
                model.ChannelActiveLocation.setLatitude(new JSONObject(locationArray.get(i).toString()).getDouble(ChannelJsonKeys.ChannelCoOrdinatesLat));
                model.ChannelActiveLocation.setLongitude(new JSONObject(locationArray.get(i).toString()).getDouble(ChannelJsonKeys.ChannelCoOrdinatesLong));
            }
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            model.ChannelStartDate = (Date) formatter.parse(root.getString(ChannelJsonKeys.ChannelTimeOfStart));
            model.ChannelEndDateTime = (Date) formatter.parse(root.getString(ChannelJsonKeys.ChannelTimeOfEnd));

            JSONObject resources = root.getJSONObject(ChannelJsonKeys.ChannelResources);

            // Profile addition
            JSONArray profilesArray = resources.getJSONArray(ChannelJsonKeys.ChannelProfiles);
            if(profilesArray.length() != 0) {
                model.Profiles = ProfileModel.getProfileListFromJsonObject(profilesArray);
                for(int i = 0; i < model.Profiles.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.Profiles.get(i).ProfileId;
                    channelResourceModel.ResourceType = ResourceType.Profiles;

                    model.Resources.add(channelResourceModel);
                }
            }

            // Urls Addition
            JSONArray urlArray = resources.getJSONArray(ChannelJsonKeys.ChannelUrls);
            if(urlArray.length() != 0) {
                model.Urls = UrlModel.getUrlFromJsonObject(urlArray);
                for(int i = 0; i < model.Urls.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.Urls.get(i).UrlId;
                    channelResourceModel.ResourceType = ResourceType.Url;
                    model.Resources.add(channelResourceModel);
                }
            }

            // Applications Addition
            JSONArray applicationArray = resources.getJSONArray(ChannelJsonKeys.ChannelApplications);
            if(applicationArray.length() != 0) {
                model.Applications = ApplicationModel.getApplicationListFromJsonObject(applicationArray);
                for(int i = 0; i < model.Applications.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.Applications.get(i).ApplicationId;
                    channelResourceModel.ResourceType = ResourceType.Application;
                    model.Resources.add(channelResourceModel);
                }
            }

            // Contacts Addition
            JSONArray contactsArray = resources.getJSONArray(ChannelJsonKeys.ChannelContacts);
            if(contactsArray.length() != 0) {
                model.Contacts = ContactModel.getContactsListFromJsonObject(contactsArray);
                for(int i = 0; i < model.Contacts.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.Contacts.get(i).ContactId;
                    channelResourceModel.ResourceType = ResourceType.Contact;
                    model.Resources.add(channelResourceModel);
                }
            }

            // Venue Addition
            JSONArray venueArray = resources.getJSONArray(ChannelJsonKeys.ChannelVenues);
            if(venueArray.length() != 0) {
                model.Venues = VenueModel.getVenueListFromJsonObject(venueArray);
                for(int i = 0; i < model.Venues.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.Venues.get(i).VenueId;
                    channelResourceModel.ResourceType = ResourceType.Venue;
                    model.Resources.add(channelResourceModel);
                }
            }

            // Venue Addition
            JSONArray chatRoomArray = resources.getJSONArray(ChannelJsonKeys.ChannelChatRooms);
            if(chatRoomArray.length() != 0) {
                model.ChatRooms = ChatRoomModel.getChatRoomListFromJsonObject(chatRoomArray);
                for(int i = 0; i < model.ChatRooms.size(); i++){
                    ChannelResourcesModel channelResourceModel = new ChannelResourcesModel();
                    channelResourceModel.ChannelId = model.ChannelId;
                    channelResourceModel.ResourceId = model.ChatRooms.get(i).ChatRoomId;
                    channelResourceModel.ResourceType = ResourceType.ChatRoom;
                    model.Resources.add(channelResourceModel);
                }
            }

            ChannelDbHandler.AddChannelToDbIfItDoesNotExist(context, model);

            return model;
            // todo inser into db
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
