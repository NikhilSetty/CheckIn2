package com.mantra.checkin.Session;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import com.mantra.checkin.DBHandlers.ChannelDbHandler;
import com.mantra.checkin.DBHandlers.SettingsInfoDBHandler;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.LocationHelpers.LocationUtility;
import com.mantra.checkin.Service.LocationMonitoringService;


/**
 * Created by nravishankar on 9/19/2016.
 */
public class SessionHelper {

    private final String TAG = "SessionHelper";

    public static String BaseUrl = "http://10.84.244.17";
    public static Resources mR;
    public static LocationUtility mLocationUtility;
    public static Location mLocation;
    public static UserInfo user;
    public static Boolean LoginStatus = false;
    public static Boolean AnySubscribedChannels = false;

    public SessionHelper(Context context){
        mR = context.getResources();
        mLocationUtility = new LocationUtility(context);
        LoginStatus = SettingsInfoDBHandler.CheckLoginStatus(context);
        if(LoginStatus){
            user = UserInfoDBHandler.FetchCurrentUserDetails(context);
        }else{
            user = new UserInfo();
        }

        // Start location monitoring service
        try{
            Intent i = new Intent(context, LocationMonitoringService.class);
            context.startService(i);
        }catch (Exception e){
            Log.e(TAG, "Service start command : " + e.getMessage());
        }

        // Check if there are any subscribed channels
        AnySubscribedChannels = ChannelDbHandler.CheckIfUserHasSubscribedToAnyChannels(context);
    }
}
