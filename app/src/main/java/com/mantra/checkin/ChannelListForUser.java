package com.mantra.checkin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.Adapters.ChannelListForUserAdapter;
import com.mantra.checkin.Adapters.VerticalSpaceItemDecoration;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.LocationJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.UserInfoJSON;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.NetworkHelpers.HttpGetter;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChannelListForUser extends AppCompatActivity {
    public static String TAG = "ChannelListForUser";
    private RecyclerView recyclerView;
    private String response = "";
    public String CheckinServerUserId = "";
    public ChannelListForUserAdapter channelListForUserAdapter;
    private static final int VERTICAL_ITEM_SPACE = 48;

    List<ChannelModel> channelModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list_for_user);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_channellistforuser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        SessionHelper.mLocation = SessionHelper.mLocationUtility.getLastKnownLocation(ChannelListForUser.this);
        UserInfo userInfo = UserInfoDBHandler.FetchCurrentUserDetails(getApplicationContext());

        populateRecyclerView();
    }

    public void populateRecyclerView() {

        AsyncTask<String, String, String> PostServerDetails = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
            }


            @Override
            protected String doInBackground(String... strings) {
                HttpPost httpPost = new HttpPost();
                try {
                    //hit properly once u get url
                    response = httpPost.post(APIUrls.BaseURl + APIUrls.RETRIEVECHANNELS,getUserIDandLocation());
                    String data = new JSONObject(response).getString("Data");
                    ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
                    switch (responseStatusCodes) {
                        case Success:
                            parseJsonResponse(data);
                            break;
                        case Error:
                            break;
                    }
                    //test
//                    response = "{\n" +
//                            "\t\"Channels\": [{\n" +
//                            "\t\t\"ChannelId\": \"2\",\n" +
//                            "\t\t\"Name\": \"\",\n" +
//                            "\t\t\"IsPublic\": \"true\",\n" +
//                            "\t\t\"IsLocationBased\": \"true\"\n" +
//                            "\t}, {\n" +
//                            "\t\t\"ChannelId\": \"30\",\n" +
//                            "\t\t\"Name\": \"\",\n" +
//                            "\t\t\"IsPublic\": \"false\",\n" +
//                            "\t\t\"IsLocationBased\": \"true\"\n" +
//                            "\t}]\n" +
//                            "}";
//                    String response2 = Utility.convertStandardJSONString(response);
//                    parseJsonResponse(response2);
                } catch (Exception e) {
                   // Log.e(TAG, e.getMessage());
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    //Response not null
                    channelListForUserAdapter = new ChannelListForUserAdapter(ChannelListForUser.this,channelModelList);
                    recyclerView.setAdapter(channelListForUserAdapter);
                }
            }
        };

        PostServerDetails.execute("");
        //addCheckInServerUserIDtoDB(response);

    }

    private void parseJsonResponse(String response) {
        channelModelList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray channelarray = jsonObject.getJSONArray("Channels");
            if (channelarray.length() == 0) {
                    //No channels around or assigned to User.Launch Blank OTP screen
                Intent i = new Intent(ChannelListForUser.this,OTP_when_NOChannels.class);
                startActivity(i);
                finish();
            } else {
                for (int i = 0; i < channelarray.length(); i++) {
                    JSONObject channelinfo = channelarray.optJSONObject(i);
                    ChannelModel channelModel = new ChannelModel();
                    channelModel.setChannelId(channelinfo.getString(ChannelJsonKeys.ChannelId));
                    channelModel.setName(channelinfo.getString(ChannelJsonKeys.ChannelName));
                    channelModel.setPublic(Boolean.valueOf(channelinfo.getString(ChannelJsonKeys.ChannelIsPublic)));
                   channelModel.setDescription(channelinfo.getString(ChannelJsonKeys.ChannelDescription));
                    channelModel.setAuthenticated(Boolean.valueOf(channelinfo.getString(ChannelJsonKeys.ChannelAuthentication)));
                    channelModelList.add(channelModel);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUserIDandLocation() throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(UserInfoJSON.USERID, SessionHelper.user.getCheckInServerUserId());
            jsonObject.put(LocationJsonKeys.LATITUDE,12.9716);//SessionHelper.mLocation.getLatitude());
            jsonObject.put(LocationJsonKeys.LONGITUDE,77.5946);//SessionHelper.mLocation.getLongitude());
            Log.d(TAG,jsonObject.toString());
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}

