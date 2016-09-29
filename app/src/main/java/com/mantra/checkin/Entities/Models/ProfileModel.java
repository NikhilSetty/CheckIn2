package com.mantra.checkin.Entities.Models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mantra.checkin.DBHandlers.ChannelDbHandler;
import com.mantra.checkin.Entities.Enums.ProfileType;
import com.mantra.checkin.Entities.Enums.WiFiConfigTypes;
import com.mantra.checkin.Entities.JSONKEYS.ProfileJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.WifiProfileJson;
import com.mantra.checkin.Utilities.WifiUtility;

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
            return settings.get(key);
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

    public static void configure_profile(Context context, ProfileModel model) throws Exception {
        switch(model.ProfileType){
            case Wifi:
                Toast.makeText(context,"Configuring and connecting wifi",Toast.LENGTH_SHORT).show();
                WifiUtility.addNetworkConfig(model.getValueOfSetting(WifiProfileJson.SSID),model.getValueOfSetting(WifiProfileJson.PASSWORD),WiFiConfigTypes.fromInteger(Integer.parseInt(model.getValueOfSetting(WifiProfileJson.SECURITYTYPE))),context);
                WifiUtility.connect(model.getValueOfSetting(WifiProfileJson.SSID),context);
                break;
            case AudioProfile:
                break;

        }
    }

    public static void get_db_model_and_configure_profile(Context context,String channelid){
        ChannelModel dbmodel = ChannelDbHandler.get_model_from_channel_id(context,channelid);
        if(dbmodel!=null && dbmodel.Profiles.size() !=0) {
            for (int i = 0; i < dbmodel.Profiles.size(); i++) {
                try {
                    ProfileModel.configure_profile(context, dbmodel.Profiles.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
