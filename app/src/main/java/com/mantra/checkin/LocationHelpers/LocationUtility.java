package com.mantra.checkin.LocationHelpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


/**
 * Created by vikoo on 05/09/15.
 */
public class LocationUtility {

    private Location currentLocation;
    private boolean isGPSEnabled, isNetworkEnabled;

    private LocationManager locationManager;
    private static LocationUtility sLocationUtility;
    private Context mCtx;

    public LocationUtility(Context ctx) {
        mCtx = ctx;
        locationManager = (LocationManager) ctx
                .getSystemService(Context.LOCATION_SERVICE);
    }

    public static LocationUtility getInstance(Context ctx) {
        if (sLocationUtility == null) {
            sLocationUtility = new LocationUtility(ctx);
        }
        return sLocationUtility;
    }

    public Location getLastKnownLocation(Context context) {
        try {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc == null) {
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            return loc;
        }catch (SecurityException e){
            return null;
        }
    }
}
