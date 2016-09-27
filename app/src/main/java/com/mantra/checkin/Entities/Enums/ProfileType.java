package com.mantra.checkin.Entities.Enums;

/**
 * Created by nravishankar on 9/25/2016.
 */
public enum ProfileType {
    Wifi,
    AudioProfile;

    public static ProfileType fromInteger(int x) {
        switch(x) {
            case 0:
                return Wifi;
            case 1:
                return AudioProfile;
        }
        return null;
    }

    public static String getStringForDb(ProfileType type) {
        switch(type) {
            case Wifi:
                return "0";
            case AudioProfile:
                return "1";
        }
        return null;
    }

    public static ProfileType getFromDbString(String string) {
        switch(string) {
            case "0":
                return Wifi;
            case "1":
                return AudioProfile;
        }
        return null;
    }
}
