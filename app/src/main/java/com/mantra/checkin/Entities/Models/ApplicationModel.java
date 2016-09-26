package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.ApplicationJsonKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class ApplicationModel {
    public String ApplicationId = "";
    public String ApplicationStoreUrl = "";
    public String ApplicationName = "";

    public static List<ApplicationModel> getApplicationListFromJsonObject(JSONArray applicationArray) throws JSONException {
        List<ApplicationModel> list = new ArrayList<>();
        for (int i = 0; i < applicationArray.length(); i++){
            ApplicationModel model = new ApplicationModel();
            JSONObject modelObject = new JSONObject(applicationArray.get(i).toString());

            model.ApplicationId = Integer.toString(modelObject.getInt(ApplicationJsonKeys.ApplicationId));
            model.ApplicationName = modelObject.getString(ApplicationJsonKeys.ApplicationName);
            model.ApplicationStoreUrl = modelObject.getString(ApplicationJsonKeys.ApplicationStoreUrl);

            list.add(model);
        }
        return list;
    }
}
