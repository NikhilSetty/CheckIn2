package com.mantra.checkin.APIURLs;

/**
 * Created by adithyar on 9/27/2016.
 */
public class APIUrls {
    public static String BaseURl = "http://10.85.193.92";
    public static String RegisterToChannel = "/CheckIn/api/UserChannelMap/RegisterToChannel";
    public static String ADDUSER = "/CheckIn/api/User/AddUser";
    public static String RESENDOTP = "/CheckIn/api/UserChannelMap/ResendOtp";
    public static String RETRIEVECHANNELS = "/CheckIn/api/Channel/RetrieveChannelsByLocationAndUser";
    public static String GETCHANNEL = "/CheckIn/api/UserChannelMap/GetChannel";
    public static String SEND_MESSAGE = "/CheckIn/api/Chat/AddChatMessage";
}
