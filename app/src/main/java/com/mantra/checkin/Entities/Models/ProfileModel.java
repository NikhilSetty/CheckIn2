package com.mantra.checkin.Entities.Models;

import android.util.Log;

import com.mantra.checkin.Entities.Enums.ProfileType;
import com.mantra.checkin.Entities.JSONKEYS.ProfileJsonKeys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static List<ProfileModel> getProfileListFromJsonObject(JSONArray profilesJsonArray) throws Exception{
        List<ProfileModel> list = new ArrayList<>();
        for(int i = 0; i < profilesJsonArray.length(); i++){
            ProfileModel model = new ProfileModel();
            JSONObject jsonModel = new JSONObject(profilesJsonArray.get(i).toString());

            model.ProfileId = Integer.toString(jsonModel.getInt(ProfileJsonKeys.ProfileId));
            model.ProfileType = com.mantra.checkin.Entities.Enums.ProfileType.fromInteger(jsonModel.getInt(ProfileJsonKeys.ProfileType));
            JSONArray profileDataArray = jsonModel.getJSONArray(ProfileJsonKeys.ProfileData);

            model.settings = ProfileSettings.getSettingsFromJsonObject(profileDataArray);
            list.add(model);
        }
        return list;
    }
}
