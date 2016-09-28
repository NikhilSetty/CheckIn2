package com.mantra.checkin.Entities.Enums;

/**
 * Created by nravishankar on 9/25/2016.
 */
public enum ResourceType {
    Profiles,
    Url,
    Application,
    Contact,
    Venue,
    ChatRoom;

    public static String getStringForDb(ResourceType resourceType) {
        switch(resourceType) {
            case Profiles:
                return "0";
            case Url:
                return "1";
            case Application:
                return "2";
            case Contact:
                return "3";
            case Venue:
                return "4";
            case ChatRoom:
                return "5";
        }
        return null;
    }

    public static ResourceType getFromDbString(String string) {
        switch(string) {
            case "0":
                return Profiles;
            case "1":
                return Url;
            case "2":
                return Application;
            case "3":
                return Contact;
            case "4":
                return Venue;
            case "5":
                return ChatRoom;
        }
        return null;
    }

    public static String getString(ResourceType type){
        switch (type){
            case Profiles:
                return "Profiles";
            case Url:
                return "Url";
            case Application:
                return "Application";
            case Contact:
                return "Contact";
            case Venue:
                return "Venue";
            case ChatRoom:
                return "ChatRoom";
        }
        return null;
    }
}
