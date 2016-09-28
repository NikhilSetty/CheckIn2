package com.mantra.checkin.NetworkHelpers;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nravishankar on 9/19/2016.
 */
public class HttpPost {

    String DEBUG_TAG = "HttpPost";

    public String post(String stringUrl, String entity) throws IOException {
        InputStream is = null;

        try {
            Log.d(DEBUG_TAG,"posting to " + stringUrl);
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.disconnect();
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            // Set the entity for the request
            if(entity != null && !entity.isEmpty()) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(entity);
                writer.flush();
                writer.close();
                os.close();
            }

            // conn.setDoInput(true);
            // Starts the query
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String sResponse = Utility.convertInputStreamToString(is);
            Log.d(DEBUG_TAG, sResponse);
            return sResponse;

        } catch (Exception e){
            Log.e(DEBUG_TAG, e.getMessage());
            return null;
        }
    }
}
