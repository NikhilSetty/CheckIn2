package com.mantra.checkin.Session;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mantra.checkin.DBHandlers.ChannelDbHandler;
import com.mantra.checkin.DBHandlers.SettingsInfoDBHandler;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.FCM.MyFireBaseInstanceIdService;
import com.mantra.checkin.LocationHelpers.LocationUtility;
import com.mantra.checkin.Service.LocationMonitoringService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by nravishankar on 9/19/2016.
 */
public class SessionHelper {

    private final String TAG = "SessionHelper";

    public static Resources mR;
    public static LocationUtility mLocationUtility;
    public static Location mLocation;
    public static UserInfo user;
    public static Boolean LoginStatus = false;
    public static Boolean AnySubscribedChannels = false;
    public static List<ChannelModel> channelModelList = new ArrayList<ChannelModel>();


    public static Stack<Fragment> fragmentStack = new Stack<Fragment>();


    public SessionHelper(Context context){
        mR = context.getResources();
        mLocationUtility = new LocationUtility(context);
        LoginStatus = SettingsInfoDBHandler.CheckLoginStatus(context);
        if(LoginStatus){
            user = UserInfoDBHandler.FetchCurrentUserDetails(context);
            MyFireBaseInstanceIdService.sendRegistrationToServer(context);
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
        //
        mLocation = mLocationUtility.getLastKnownLocation(context);

        // Check if there are any subscribed channels
        AnySubscribedChannels = ChannelDbHandler.CheckIfUserHasSubscribedToAnyChannels(context);
        if(AnySubscribedChannels){
            channelModelList = ChannelDbHandler.getAllChannelsAndDetails(context);
        }else {
            // test
//        ChannelModel model = ChannelModel.addChannelToDbAndGetModelFromJson(context, "{\n" +
//                "        \"ChannelId\": 2,\n" +
//                "        \"Name\": \"Sequoia\",\n" +
//                "        \"IsPublic\": false,\n" +
//                "        \"IsLocationBased\": false,\n" +
//                "        TimeOfActivation: \"26-09-2016 02:43:11\",\n" +
//                "        TimeOfDeactivation: \"26-09-2016 02:43:11\",\n" +
//                "        \"Co-ordinates\": [{\n" +
//                "            \"Latitude\": 12.9716,\n" +
//                "            \"Longitude\": 77.5946\n" +
//                "        }],\n" +
//                "        \"Resources\": {\n" +
//                "            \"ChatRooms\": [\n" +
//                "                {\n" +
//                "                    \"ChatRoomId\": 1,\n" +
//                "                    \"Name\": \"Channel1\",\n" +
//                "                    \"ChatMessages\": [\n" +
//                "                        {\n" +
//                "                            \"ChatMessageId\": 2,\n" +
//                "                            \"ChatRoomId\": 1,\n" +
//                "                            \"UserId\": 3007,\n" +
//                "                            \"Message\": \"fsociety\",\n" +
//                "                            \"IsAdminMessage\": false,\n" +
//                "                            \"IsImage\": false,\n" +
//                "                            \"TimeOfGeneration\": \"26-09-2016 02:43:11 PM\",\n" +
//                "                            \"UserName\" : \"Anthony\"\n" +
//                "\n" +
//                "                        },\n" +
//                "                        {\n" +
//                "                            \"ChatMessageId\": 3,\n" +
//                "                            \"ChatRoomId\": 1,\n" +
//                "                            \"UserId\": 3007,\n" +
//                "                            \"Message\": \"fsociety again\",\n" +
//                "                            \"IsAdminMessage\": false,\n" +
//                "                            \"IsImage\": false,\n" +
//                "                            \"TimeOfGeneration\": \"26-09-2016 02:50:11 PM\",\n" +
//                "                            \"UserName\" : \"Anthony\"\n" +
//                "                        }\n" +
//                "                    ]\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"Profiles\": [\n" +
//                "                {\n" +
//                "                    \"ProfileId\": 1,\n" +
//                "                    \"Type\": 0,\n" +
//                "                    \"Data\": [\n" +
//                "                        {\n" +
//                "                            \"Key\": \"Name\",\n" +
//                "                            \"Value\": \"nikhil\"\n" +
//                "                        }\n" +
//                "                    ]\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"WebClips\": [\n" +
//                "                {\n" +
//                "                    \"WebClipId\": 1,\n" +
//                "                    \"WebClipName\": \"Adventure\",\n" +
//                "                    \"WebClipUrl\": \"http://youtube/adven\",\n" +
//                "                    \"IconUrl\": \"http://something\",\n" +
//                "                    \"OpenInBrowser\": false\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"Applications\": [\n" +
//                "                {\n" +
//                "                    \"ApplicationId\": 1,\n" +
//                "                    \"ApplicationName\": \"Boxer\",\n" +
//                "                    \"ApplicationUrl\": \"http://host\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"Locations\": [\n" +
//                "                {\n" +
//                "                    \"LocationId\": 1,\n" +
//                "                    \"LocationName\": \"Ganga\",\n" +
//                "                    \"Latitude\": 12,\n" +
//                "                    \"Longitude\": 72\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"Contacts\": [\n" +
//                "                {\n" +
//                "                \t\"ContactId\" : 2,\n" +
//                "                    \"ContactName\": \"Nikhil\",\n" +
//                "                    \"ContactNumber\": \"9090909090\",\n" +
//                "                    \"Title\": \"SOmething\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    }");
    }
    }
}
