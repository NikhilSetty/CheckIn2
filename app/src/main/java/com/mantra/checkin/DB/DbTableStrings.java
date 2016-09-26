package com.mantra.checkin.DB;

public class DbTableStrings {

    //Strings for UserInfo
    public static final String TABLE_NAME_USER_INFO = "user_info";

    public static final String USERNAME = "user_name";
    public static final String USEREMAIL = "user_email";
    public static final String USERPHOTO = "user_photo";
    public static final String FIRSTNAME = "user_first_name";
    public static final String LASTNAME = "user_last_name";
    public static final String USERID = "user_id";
    public static final String USER_PHONE_NUMBER = "user_phone_number";
    public static final String CHECKIN_SERVER_USERID = "checkin_server_userid";

    //Strings for Settings
    public static final String TABLE_NAME_SETTINGS_INFO = "settings_info";

    public static final String SETTINGS_KEYS = "keys";
    public static final String SETTINGS_VALUES = "sValues";


    // Channel Settings
    public static final String TABLE_NAME_CHANNEL = "channels";

    public static final String CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String CHANNEL_ISPUBLIC = "channel_is_public";
    public static final String CHANNEL_IS_LOCATION_BASED  = "channel_is_location_based";
    public static final String CHANNEL_LATTITUDE = "channel_lat";
    public static final String CHANNEL_LONGTITUDE = "channel_long";
    public static final String CHANNEL_TIME_OF_START = "channel_time_of_start";
    public static final String CHANNEL_TIME_OF_END = "channel_time_of_end";
    public static final String CHANNEL_IS_ACTIVE = "channel_is_active";

    // Resources Table
    public static final String TABLE_NAME_RESOURCES = "resources";

    public static final String RESOURCES_CHANNEL_ID = "resources_channel_id";
    public static final String RESOURCES_TYPE = "resources_type";
    public static final String RESOURCES_ID = "resources_id";



    // Profiles Table
    public static final String TABLE_NAME_PROFILES = "profiles";

    public static final String PROFILES_TYPE = "profiles_type";
    public static final String PROFILES_REFERENCE_FOREIGN_KEY = "resources_id";

    // Profile Settings table
    public static final String TABLE_NAME_PROFILES_SETTINGS = "profile_settings";

    public static final String PROFILE_ID_FOREIGN_KEY = "profiles_id";
    public static final String PROFILE_SETTINGS_KEY = "profile_settings_key";
    public static final String PROFILE_SETTINGS_VALUES = "profile_settings_value";

    // Applications Table
    public static final String TABLE_NAME_APPLICATIONS = "applications";

    public static final String APPLICATION_REFERENCE_FOREIGN_KEY = "resource_id";
    public static final String APPLICATION_STORE_URL = "application_store_url";
    public static final String APPLICATION_NAME = "application_name";

    // Contacts Table
    public static final String TABLE_NAME_CONTACTS = "contacts";

    public static final String CONTACTS_REFERENCE_FOREIGN_KEY = "resource_id";
    public static final String CONTACT_NAME = "contacts_name";
    public static final String CONTACT_NUMBER = "contacts_number";
    public static final String CONTACT_POSITION = "contacts_position";

    // venue lcoations
    public static final String TABLE_NAME_VENUE_DETAILS = "venue";

    public static final String VENUE_REFERENCE_FOREIGN_KEY = "resource_id";
    public static final String VENUE_NAME = "venue_name";
    public static final String VENUE_lat = "venue_lat";
    public static final String VENUE_long = "venue_long";

    // Chat Rooms
    public static final String TABLE_NAME_CHAT_ROOMS = "chat_rooms";

    public static final String CHAT_ROOMS_REFERENCE_FOREIGN_KEY = "resource_id";
    public static final String CHAT_ROOMS_NAME = "chat_rooms_name";

    // Chat Messages
    public static final String TABLE_NAME_CHAT_MESSAGES = "chat_messages";

    public static final String CHAT_MESSAGES_REFERENCE_FOREIGN_KEY = "resource_id";
    public static final String CHAT_MESSAGES_ID = "chat_messages_id";
    public static final String CHAT_MESSAGES_IS_IMAGE = "chat_messages_is_image";
    public static final String CHAT_MESSAGES_IS_ADMIN_MESSAGE = "chat_messages_is_admin";
    public static final String CHAT_MESSAGES_MESSAGE = "chat_messages_message";
    public static final String CHAT_MESSAGES_TIME = "chat_messages_time_stamp";
    public static final String CHAT_MESSAGES_URL = "chat_messages_url";

    // Url
    public static final String TABLE_NAME_URL = "urls";

    public static final String URL_ID = "url_id";
    public static final String URL_ADDRESS = "url_address";
    public static final String URL_NAME = "url_name";
    public static final String URL_ICON_URL = "url_icon_url";
    public static final String URL_OPEN_IN_BROWSER = "url_open_in_browser";
}
