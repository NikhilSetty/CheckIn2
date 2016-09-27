package com.mantra.checkin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mantra.checkin.Adapters.ChannelListForUserAdapter;
import com.mantra.checkin.Adapters.VerticalSpaceItemDecoration;
import com.mantra.checkin.DBHandlers.UserInfoDBHandler;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.Entities.Models.UserInfo;
import com.mantra.checkin.NetworkHelpers.HttpGetter;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChannelListForUser extends AppCompatActivity {
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
        SessionHelper.mLocation = SessionHelper.mLocationUtility.getLastKnownLocation(getApplicationContext());
        UserInfo userInfo = UserInfoDBHandler.FetchCurrentUserDetails(getApplicationContext());
        CheckinServerUserId = userInfo.getCheckInServerUserId();

        populateRecyclerView();
    }

    public void populateRecyclerView() {

        AsyncTask<String, String, String> PostServerDetails = new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
            }


            @Override
            protected String doInBackground(String... strings) {
                HttpGetter httpGetter = new HttpGetter();
                try {
                    ///hit properly once u get url
                  //  response = httpGetter.httpGetter(SessionHelper.BaseUrl + "/CheckIn/api/User/AddUser",CheckinServerUserId+SessionHelper.mLocation);
//                    ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
//                    switch (responseStatusCodes) {
//                        case Success:
//                            parseJsonResponse(response);
//
//                            break;
//                        case Error:
//                            break;
//                    }
                    response = "{\n" +
                            "\t\"Channels\": [{\n" +
                            "\t\t\"ChannelId\": \"20\",\n" +
                            "\t\t\"Name\": \"\",\n" +
                            "\t\t\"IsPublic\": \"true\",\n" +
                            "\t\t\"IsLocationBased\": \"true\"\n" +
                            "\t}, {\n" +
                            "\t\t\"ChannelId\": \"30\",\n" +
                            "\t\t\"Name\": \"\",\n" +
                            "\t\t\"IsPublic\": \"false\",\n" +
                            "\t\t\"IsLocationBased\": \"true\"\n" +
                            "\t}]\n" +
                            "}";
                    String response2 = Utility.convertStandardJSONString(response);
                    parseJsonResponse(response2);
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

                    channelModelList.add(channelModel);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
