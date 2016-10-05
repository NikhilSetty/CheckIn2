package com.mantra.checkin.Entities.Enums;

/**
 * Created by nravishankar on 9/22/2016.
 */
public enum WiFiConfigTypes {
    WEP,
    WPA,
    Open;

    public static WiFiConfigTypes fromInteger(int x) {
        switch(x) {
            case 0:
                return WEP;
            case 1:
                return WPA;
            case 2:
                return Open;
        }
        return null;
    }
}
