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
            + DbTableStrings.USERID + " string) ";

    public static final String CREATE_TABLE_SETTINGS_INFO = "create table if not exists " + DbTableStrings.TABLE_NAME_SETTINGS_INFO +
            "( _id integer primary key autoincrement, "
            + DbTableStrings.ISLOGGEDIN + " boolean) ";
}


