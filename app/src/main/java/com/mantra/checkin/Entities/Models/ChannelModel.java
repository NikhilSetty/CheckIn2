package com.mantra.checkin.Entities.Models;

import android.location.Location;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ChannelModel {
    public String ChannelId = "";
    public String Name = "";
    public Boolean IsPublic = true;
    public Boolean IsLocationBased = false;
    public Location ChannelActiveLocation = new Location("");
    public Date ChannelStartDate = new Date();
    public Date ChannelEndDateTime = new Date();

    public List<ChannelResourcesModel> Resources = new ArrayList<>();

    public List<ProfileModel> Profiles = new ArrayList<ProfileModel>();
    public List<UrlModel> WebClips = new ArrayList<UrlModel>();
    public List<ApplicationModel> Applications = new ArrayList<ApplicationModel>();
    public List<ContactModel> Contacts = new ArrayList<ContactModel>();
    public List<VenueModel> Venues = new ArrayList<VenueModel>();
    public List<ChatRoomModel> ChatRooms = new ArrayList<ChatRoomModel>();


    public ChannelModel(){
        ChannelActiveLocation.setLatitude(0);
        ChannelActiveLocation.setLongitude(0);
    }
}
