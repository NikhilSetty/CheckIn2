package com.mantra.checkin.Entities.Models;

import android.location.Location;

import com.mantra.checkin.Entities.JSONKEYS.VenueJsonKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<VenueModel> getVenueListFromJsonObject(JSONArray venueArray) throws JSONException {
        List<VenueModel> list = new ArrayList<>();
        for(int i = 0; i < venueArray.length(); i++){
            VenueModel model = new VenueModel();
            JSONObject modelObject = new JSONObject(venueArray.get(i).toString());

            model.VenueId = Integer.toString(modelObject.getInt(VenueJsonKeys.VenueId));
            model.VenueName = modelObject.getString(VenueJsonKeys.VenueName);

            model.VenueLocation.setLatitude(modelObject.getDouble(VenueJsonKeys.VenueLat));
            model.VenueLocation.setLongitude(modelObject.getDouble(VenueJsonKeys.VenueLong));

            list.add(model);
        }

        return list;
    }
}
