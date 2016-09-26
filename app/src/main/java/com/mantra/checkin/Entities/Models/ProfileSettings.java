package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.ProfileSettingsJsonKeys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ProfileSettings {
    public String ProfileId = "";
    public String ProfileSettingKey = "";
    public String ProfileSettingValue = "";

    public static Map<String, String> getSettingsFromJsonObject(JSONArray profileDataArray) throws Exception{
        Map<String, String> settingsMap = new HashMap<>();
        for (int i = 0; i < profileDataArray.length(); i++ ){
            JSONObject settingsObject = new JSONObject(profileDataArray.get(i).toString());
            settingsMap.put(settingsObject.getString(ProfileSettingsJsonKeys.ProfileSettingsKey), settingsObject.getString(ProfileSettingsJsonKeys.ProfileSettingsValue));
        }
        return settingsMap;
    }
}
