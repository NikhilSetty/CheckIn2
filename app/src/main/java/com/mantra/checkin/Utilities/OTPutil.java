package com.mantra.checkin.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.Entities.JSONKEYS.ChannelJsonKeys;
import com.mantra.checkin.Entities.JSONKEYS.OTPJsonkeys;
import com.mantra.checkin.Entities.JSONKEYS.UserInfoJSON;
import com.mantra.checkin.Entities.Models.ChannelModel;
import com.mantra.checkin.MainActivity;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.R;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adithyar on 9/27/2016.
 */
public class OTPutil {
    public static Boolean TokenStatus;
    static String TAG = "OTP";


    public static String create_otp_json(String token, String channelid) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject tokenjson = new JSONObject();
        tokenjson.put(UserInfoJSON.USERID, SessionHelper.user.getCheckInServerUserId());
        if (channelid.length() == 0) {
            tokenjson.put(ChannelJsonKeys.ChannelId, "");
        } else {
            tokenjson.put(ChannelJsonKeys.ChannelId, channelid);
        }

        tokenjson.put(OTPJsonkeys.Token, token);
//test
//        tokenjson.put(UserInfoJSON.USERID, "2013");
//        tokenjson.put(ChannelJsonKeys.ChannelId,"1");
//        tokenjson.put(OTPJsonkeys.Token,token);
//        jsonObject.put(OTPJsonkeys.OTP, tokenjson);
        return jsonObject.toString();
    }

    public static String create_resend_otp_json(String channelid) throws JSONException {
        JSONObject tokenjson = new JSONObject();
                tokenjson.put(UserInfoJSON.USERID, SessionHelper.user.getCheckInServerUserId());
        if (channelid.length() == 0) {
            tokenjson.put(ChannelJsonKeys.ChannelId, "");
        } else {
            tokenjson.put(ChannelJsonKeys.ChannelId, channelid);
        }
        //tokenjson.put(UserInfoJSON.USERID, "2013");
        //tokenjson.put(ChannelJsonKeys.ChannelId,"1");
        return tokenjson.toString();
    }

    public static void postOTPTokenandLogin(final Context context, final String json) {


        AsyncTask<String, String, Boolean> sendotptokenToServer = new AsyncTask<String, String, Boolean>() {
            String response = "";
            public ProgressDialog mProgressDialog;


            @Override
            protected void onPreExecute() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage("Authenticating");
                mProgressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mProgressDialog.dismiss();
                Log.d("OTP",aBoolean.toString());
                if (aBoolean) {
                    Log.d(TAG,"inside true");
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Invalid token", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                HttpPost httpPost = new HttpPost();
                try {
                    Log.d(TAG, json);
                    response = httpPost.post(APIUrls.BaseURl + APIUrls.RegisterToChannel, json);
                    ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
                    switch (responseStatusCodes) {
                        case Success:
                            //ChannelModel model = ChannelModel.addChannelToDbAndGetModelFromJson(response);
                            //SessionHelper.channelModelList.add(model);
                            Toast.makeText(context, "Authentication Successful", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "returning true");
                            return true;
                        case Error:
                            Toast.makeText(context, "Invalid Token", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "returning false");
                            return false;
                    }
                } catch (Exception e) {
                }
                Log.d(TAG, "returning false");
                return false;
            }
        };
        sendotptokenToServer.execute("");
    }
    public static void ResendOTPtoken(final Context context, final String json){
        AsyncTask<String, String, Boolean> requestnewtoken = new AsyncTask<String, String, Boolean>() {
            String response = "";
            public ProgressDialog mProgressDialog;


            @Override
            protected void onPreExecute() {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage("Requesting new token");
                mProgressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mProgressDialog.dismiss();
                if(aBoolean){
                    Toast.makeText(context, "Token Requested Successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Token Request Failed", Toast.LENGTH_LONG).show();

                }
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                HttpPost httpPost = new HttpPost();
                try {
                    response = httpPost.post(APIUrls.BaseURl + APIUrls.RESENDOTP, json);
                    ResponseStatusCodes responseStatusCodes = Utility.getResponseStatus(response);
                    switch (responseStatusCodes) {
                        case Success:
                            return true;
                        case Error:
                            return false;
                    }
                } catch (Exception e) {
                }
                return false;
            }
        };
        requestnewtoken.execute("");
    }
}
