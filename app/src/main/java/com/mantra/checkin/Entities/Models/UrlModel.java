package com.mantra.checkin.Entities.Models;

import com.mantra.checkin.Entities.JSONKEYS.UrlJsonKeys;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nravishankar on 9/25/2016.
 */
public class UrlModel {
    public String UrlId = "";
    public String UrlAddress = "";
    public String UrlName = "";
    public String UrlIconAddress = "";
    public Boolean OpenInBrowser = false;

    public static List<UrlModel> getUrlFromJsonObject(JSONArray urlArray) throws Exception{
        List<UrlModel> list = new ArrayList<>();
        for (int i = 0; i < urlArray.length(); i++){
            UrlModel model = new UrlModel();
            JSONObject modelObject = new JSONObject(urlArray.get(i).toString());

            model.UrlId = Integer.toString(modelObject.getInt(UrlJsonKeys.UrlId));
            model.UrlAddress = modelObject.getString(UrlJsonKeys.UrlAddress);
            model.UrlName = modelObject.getString(UrlJsonKeys.UrlName);
            model.UrlIconAddress = modelObject.getString(UrlJsonKeys.UrlIconAddress);
            model.OpenInBrowser = (modelObject.getString(UrlJsonKeys.UrlOpenInBrowser)).equals("true");

            list.add(model);
        }
        return list;
    }
}
