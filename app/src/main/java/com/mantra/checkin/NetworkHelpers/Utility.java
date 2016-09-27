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
import java.nio.charset.StandardCharsets;

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
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        String response = sb.toString();
        response = response.replace("\\\"", "\"");
        return (response.substring(1, response.length() - 1));
    }

    public static String convertStandardJSONString(String data_json) {
        data_json = data_json.replaceAll("\\\\r\\\\n", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }

    public static ResponseStatusCodes getResponseStatus(String response){
        try{

            JSONObject json = new JSONObject(response);
            JSONObject status = json.getJSONObject(Constants.responseStringStatus);
            int code = status.getInt(Constants.responseIntStatusCode);
            Log.d("Status", ResponseStatusCodes.fromInteger(code).toString());
            return ResponseStatusCodes.fromInteger(code);

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        return ResponseStatusCodes.Error;
    }
}
