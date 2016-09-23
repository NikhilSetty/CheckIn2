package com.mantra.checkin.Entities.Models;

/**
 * Created by adithyar on 9/21/2016.
 */
public class UserInfo {
    public String UserName = "";
    public String UserEmail = "";
    public String UserPhoto = "";
    public String FirstName = "";
    public String LastName = "";
    //UserID from google
    public String UserID = "";
    //CheckInServerUserId from CheckIn Server
    public String CheckInServerUserId = "";
    public String RemotePhotoServerURL = "";
    public String PhoneNumber = "";
    public int _id = 0;


    public String getRemotePhotoServerURL() {
        return RemotePhotoServerURL;
    }

    public void setRemotePhotoServerURL(String remotePhotoServerURL) {
        RemotePhotoServerURL = remotePhotoServerURL;
    }

    public String getCheckInServerUserId() {
        return CheckInServerUserId;
    }

    public void setCheckInServerUserId(String checkInServerUserId) {
        CheckInServerUserId = checkInServerUserId;
    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
