package com.mantra.checkin.Entities.Models;

import android.location.Location;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class VenueModel {
    public String VenueId = "";
    public String VenueName = "";
    public Location VenueLocation = new Location("");

    public VenueModel(){
        VenueLocation.setLongitude(0);
        VenueLocation.setLatitude(0);
    }
}
