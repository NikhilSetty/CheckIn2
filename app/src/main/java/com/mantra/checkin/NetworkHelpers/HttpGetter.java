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
public class HttpGetter {

    String DEBUG_TAG = "HttpsGetter";

    public String httpGetter(String stringUrl, String entity) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept","*/*");

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

            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = Utility.convertInputStreamToString(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
