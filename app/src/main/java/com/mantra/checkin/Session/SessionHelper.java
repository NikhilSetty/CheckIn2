package com.mantra.checkin.Session;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;

import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.LocationHelpers.LocationUtility;
import com.mantra.checkin.SignUp.LoginActivity;


/**
 * Created by nravishankar on 9/19/2016.
 */
public class SessionHelper {

    public static String BaseUrl = "http://10.85.194.227:2323";
    public static Resources mR;
    public static LocationUtility mLocationUtility;
    public static Location mLocation;
    public static UserInfo user;

    public SessionHelper(Context context){
        mR = context.getResources();
        mLocationUtility = new LocationUtility(context);
        if (!UserInfoDBHandler.CheckIfUserExistsInDB(context.getApplicationContext())) {
            user = UserInfoDBHandler.FetchCurrentUserDetails(context);
        }else{
            user = new UserInfo();
        }
    }
}
