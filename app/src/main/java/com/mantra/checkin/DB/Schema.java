package com.mantra.checkin.DB;

public class Schema {
    //Create table UserInfo
    public static final String CREATE_TABLE_USERINFO = "create table if not exists " + DbTableStrings.TABLE_NAME_USER_INFO +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.USERNAME + " string, "
            + DbTableStrings.USEREMAIL + " string, "
            + DbTableStrings.USERPHOTO + " string, "
            + DbTableStrings.FIRSTNAME + " string, "
            + DbTableStrings.LASTNAME + " string, "
            + DbTableStrings.USER_PHONE_NUMBER + " string, "
            + DbTableStrings.CHECKIN_SERVER_USERID + " string, "
            + DbTableStrings.USERID + " VARCHAR(255)) ";

    public static final String CREATE_TABLE_SETTINGS_INFO = "create table if not exists " + DbTableStrings.TABLE_NAME_SETTINGS_INFO +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.SETTINGS_KEYS + " string, "
            + DbTableStrings.SETTINGS_VALUES + " string) ";

    public static final String CREATE_TABLE_CHANNEL = "create table if not exists " + DbTableStrings.TABLE_NAME_CHANNEL +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.CHANNEL_ID + " string, "
            + DbTableStrings.CHANNEL_IS_LOCATION_BASED + " string, "
            + DbTableStrings.CHANNEL_ISPUBLIC + " string, "
            + DbTableStrings.CHANNEL_LATTITUDE + " string, "
            + DbTableStrings.CHANNEL_LONGTITUDE + " string, "
            + DbTableStrings.CHANNEL_NAME + " string, "
            + DbTableStrings.CHANNEL_IS_ACTIVE + " string, "
            + DbTableStrings.CHANNEL_TIME_OF_END + " string, "
            + DbTableStrings.CHANNEL_TIME_OF_START + " string) ";

    public static final String CREATE_TABLE_RESOURCES = "create table if not exists " + DbTableStrings.TABLE_NAME_RESOURCES +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.RESOURCES_CHANNEL_ID + " string, "
            + DbTableStrings.RESOURCES_ID + " string, "
            + DbTableStrings.RESOURCES_TYPE + " string) ";

    public static final String CREATE_TABLE_PROFILES = "create table if not exists " + DbTableStrings.TABLE_NAME_PROFILES +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.PROFILES_REFERENCE_FOREIGN_KEY + " string, "
            + DbTableStrings.PROFILES_TYPE + " string) ";


    public static final String CREATE_TABLE_PROFILE_SETTINGS  = "create table if not exists " + DbTableStrings.TABLE_NAME_PROFILES_SETTINGS +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.PROFILE_SETTINGS_KEY + " string, "
            + DbTableStrings.PROFILE_ID_FOREIGN_KEY + " string, "
            + DbTableStrings.PROFILE_SETTINGS_VALUES + " string) ";

    public static final String CREATE_TABLE_NAME_APPLICATIONS  = "create table if not exists " + DbTableStrings.TABLE_NAME_APPLICATIONS +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.APPLICATION_NAME + " string, "
            + DbTableStrings.APPLICATION_REFERENCE_FOREIGN_KEY+ " string, "
            + DbTableStrings.APPLICATION_STORE_URL + " string) ";

    public static final String CREATE_TABLE_NAME_CONTACTS  = "create table if not exists " + DbTableStrings.TABLE_NAME_CONTACTS +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.CONTACT_NAME  + " string, "
            + DbTableStrings.CONTACT_NUMBER + " string, "
            + DbTableStrings.CONTACTS_REFERENCE_FOREIGN_KEY + " string, "
            + DbTableStrings.CONTACT_POSITION + " string) ";

    public static final String CREATE_TABLE_NAME_VENUE_DETAILS  = "create table if not exists " + DbTableStrings.TABLE_NAME_VENUE_DETAILS +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.VENUE_lat + " string, "
            + DbTableStrings.VENUE_long + " string, "
            + DbTableStrings.VENUE_REFERENCE_FOREIGN_KEY + " string, "
            + DbTableStrings.VENUE_NAME+ " string) ";

    public static final String CREATE_TABLE_NAME_CHAT_ROOMS  = "create table if not exists " + DbTableStrings.TABLE_NAME_CHAT_ROOMS +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.CHAT_ROOMS_NAME + " string, "
            + DbTableStrings.CHAT_ROOMS_REFERENCE_FOREIGN_KEY + " string) ";

    public static final String CREATE_TABLE_NAME_CHAT_MESSAGES  = "create table if not exists " + DbTableStrings.TABLE_NAME_CHAT_MESSAGES +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.CHAT_MESSAGES_ID + " string, "
            + DbTableStrings.CHAT_MESSAGES_IS_ADMIN_MESSAGE + " string, "
            + DbTableStrings.CHAT_MESSAGES_IS_IMAGE + " string, "
            + DbTableStrings.CHAT_MESSAGES_MESSAGE + " string, "
            + DbTableStrings.CHAT_MESSAGES_REFERENCE_FOREIGN_KEY + " string, "
            + DbTableStrings.CHAT_MESSAGES_TIME + " string, "
            + DbTableStrings.CHAT_MESSAGES_NAME + " string, "
            + DbTableStrings.CHAT_MESSAGES_URL + " string) ";

    public static final String CREATE_TABLE_URL  = "create table if not exists " + DbTableStrings.TABLE_NAME_URL +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.URL_ADDRESS + " string, "
            + DbTableStrings.URL_ICON_URL + " string, "
            + DbTableStrings.URL_ID + " string, "
            + DbTableStrings.URL_NAME + " string, "
            + DbTableStrings.URL_OPEN_IN_BROWSER+ " string) ";
}


