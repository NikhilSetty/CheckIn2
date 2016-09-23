package com.mantra.checkin.Utilities;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.mantra.checkin.Entities.Enums.WiFiConfigTypes;

import java.util.List;

/**
 * Created by nravishankar on 9/22/2016.
 */
public class WifiUtility {

    private final static String TAG = "WifiUtility";

    // Add network configuration to the device
    public static void addNetworkConfig(String ssid, String password, WiFiConfigTypes type, Context context) throws Exception {
        // Defensive
        if(ssid == null || ssid.isEmpty()){
            throw new Exception("Wifi Configuration failed. SSID - empty");
        }else if(type != WiFiConfigTypes.Open && (password == null || password.isEmpty())){
            throw new Exception("Wifi Configuration failed. Password empty");
        }

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + ssid + "\"";

        switch (type){
            case WEP:
                wifiConfiguration.wepKeys[0] = "\"" + password + "\"";
                wifiConfiguration.wepTxKeyIndex = 0;
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                break;
            case WPA:
                wifiConfiguration.preSharedKey = "\""+ password +"\"";
                break;
            case Open:
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;
            default:
                throw new Exception("Wifi Configuration failed. type invalid");
        }

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(wifiConfiguration);
    }

    // method to connect to the mentioned SSID
    // returns false if the SSID was not found
    public static boolean connect(String ssid, Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                return true;
            }
        }
        return false;
    }

    // Method to delete the specified SSID
    public static boolean deleteNetwork(String ssid, Context context){
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                    wifiManager.removeNetwork(i.networkId);
                    return true;
                }
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return false;
    }
}
