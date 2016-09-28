package com.mantra.checkin.FCM;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mantra.checkin.APIURLs.APIUrls;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;
import com.mantra.checkin.NetworkHelpers.HttpPost;
import com.mantra.checkin.NetworkHelpers.Utility;
import com.mantra.checkin.Session.SessionHelper;

import org.json.JSONObject;

/**
 * Created by nravishankar on 8/29/2016.
 */
public class MyFireBaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFireBaseIDService";

    private static String token = "";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        token = refreshedToken;
        sendRegistrationToServer(getApplicationContext());
    }

    public static String getToken(){

        return FirebaseInstanceId.getInstance().getToken();
    }

    public static void sendRegistrationToServer(final Context context ) {
        AsyncTask<String, Boolean, Boolean> sendRegIdToServer = new AsyncTask<String, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(String[] params) {
                Log.d(TAG,SessionHelper.LoginStatus.toString());
                String token = FirebaseInstanceId.getInstance().getToken();
                if(token == null) {
                    Log.e(TAG, "token is empty.");
                    return false;
                }
                if(SessionHelper.LoginStatus && !token.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject();

                        json.put("CheckInServerUserId", SessionHelper.user.getCheckInServerUserId());
                        json.put("RegistrationId", token);
                        String jsonEntity = json.toString();
                        String response = new HttpPost().post(APIUrls.BaseURl + "/CheckIn/api/User/UpdateUserRegistrationId", jsonEntity);

                        ResponseStatusCodes statusCodes = Utility.getResponseStatus(response);

                        switch (statusCodes) {
                            case Success:
                                return true;
                            case Error:
                                return false;
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                Log.e(TAG, "User not logged in yet or token is empty.");
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean) {
                    Toast.makeText(context, "Registeration Successful", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(context, "Registeration Failed", Toast.LENGTH_LONG).show();
                }
            }
        };
        sendRegIdToServer.execute("");
    }
}
