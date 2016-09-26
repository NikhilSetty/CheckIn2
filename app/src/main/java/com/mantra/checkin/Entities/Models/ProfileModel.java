package com.mantra.checkin.Entities.Models;

import android.util.Log;

import com.mantra.checkin.Entities.Enums.ProfileType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ProfileModel {

    private final static String TAG = "ProfileModel";

    public String ProfileId = "";
    public ProfileType ProfileType = null;

    public Map<String, String> settings = new HashMap<>();

    public String getValueOfSetting(String key){
        try{
            settings.get(key);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public void getWifiProfile(){
        // todo build default WIFI profile creator.
    }
}
