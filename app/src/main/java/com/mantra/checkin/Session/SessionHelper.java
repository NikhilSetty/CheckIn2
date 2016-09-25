package com.mantra.checkin.Session;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import com.mantra.checkin.DBHandlers.SettingsInfoDBHandler;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.Models.SettingsInfo;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.LocationHelpers.LocationUtility;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.Service.LocationMonitoringService;
import com.mantra.checkin.SignUp.LoginActivity;


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
    public static Boolean loginstatus;

    public SessionHelper(Context context){
        mR = context.getResources();
        mLocationUtility = new LocationUtility(context);
        loginstatus = SettingsInfoDBHandler.CheckLoginStatus(context);
        if(loginstatus){
            user = UserInfoDBHandler.FetchCurrentUserDetails(context);
        }else{
            user = new UserInfo();
        }

        try{
            Intent i = new Intent(context, LocationMonitoringService.class);
            context.startService(i);
        }catch (Exception e){
            Log.e(TAG, "Service start command : " + e.getMessage());
        }
    }
}
