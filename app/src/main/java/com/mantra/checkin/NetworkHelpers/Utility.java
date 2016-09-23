package com.mantra.checkin.NetworkHelpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mantra.checkin.Entities.Constants;
import com.mantra.checkin.Entities.Enums.ResponseStatusCodes;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nravishankar on 9/19/2016.
 */
public class Utility {

    static String TAG = "Utility";

    public static boolean isNetworkAvailable(Context context){
        try{

            ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static ResponseStatusCodes getResponseStatus(String response){
        try{

            JSONObject json = new JSONObject(response);
            JSONObject status = json.getJSONObject(Constants.responseStringStatus);
            return ResponseStatusCodes.fromInteger(status.getInt(Constants.responseIntStatusCode));

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        return ResponseStatusCodes.Error;
    }
}
