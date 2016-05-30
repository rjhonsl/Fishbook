package com.santeh.rjhonsl.fishbook.Utils;

import android.app.Application;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by rjhonsl on 8/12/2015.
 */
public class VarFishBook extends Application {


    public int currentuser;
    public int currentuserLvl;

    private int isactive;
    private int isAquaActive;
    private int isPetOneActive;
    private int isHogsActive;
    private String currentUserID;
    private String currentUserName;
    private String currentUserFirstname;
    private String currentUserLastname;
    private String currentPassword;
    private String dateAddedToDB;
    private String assignedArea;
    private String deviceID;

    private String main_Address;

    private String comment_Id;
    private String comment_MainId;
    private String comment_UID;
    private String comment_content;
    private String comment_dateCommented;
    private String comment_loclat;
    private String comment_loclong;
    private String comment_fetchat;

    private String content_id;
    private String content_mainid;
    private String content_type;
    private String content_description;
    private String content_imageUrl;
    private String content_event;
    private String content_fileURL;
    private String content_fetchat;

    private String subcomm_id;
    private String subcomm_commenid;
    private String subcomm_uid;
    private String subcomm_content;
    private String subcomm_dateCommented;
    private String subcomm_loclat;
    private String subcomm_loclong;
    private String subcomm_fetchat;

    private String main_id;
    private String main_mainuid;
    private String main_date;
    private String main_loclat;
    private String main_loclong;
    private String main_fetchat;
    private String main_seenState;





    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    private GoogleMap googleMap;


    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserFirstname() {
        return currentUserFirstname;
    }

    public void setCurrentUserFirstname(String currentUserFirstname) {
        this.currentUserFirstname = currentUserFirstname;
    }

    public String getCurrentUserLastname() {
        return currentUserLastname;
    }

    public void setCurrentUserLastname(String currentUserLastname) {
        this.currentUserLastname = currentUserLastname;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public int getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(int currentuser) {
        this.currentuser = currentuser;
    }

    public int getCurrentuserLvl() {
        return currentuserLvl;
    }

    public void setCurrentuserLvl(int currentuserLvl) {
        this.currentuserLvl = currentuserLvl;
    }

    public String getAssignedArea() {
        return assignedArea;
    }

    public void setAssignedArea(String assignedArea) {
        this.assignedArea = assignedArea;
    }

    public String getDateAddedToDB() {
        return dateAddedToDB;
    }

    public void setDateAddedToDB(String dateAddedToDB) {
        this.dateAddedToDB = dateAddedToDB;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getComment_Id() {
        return comment_Id;
    }

    public void setComment_Id(String comment_Id) {
        this.comment_Id = comment_Id;
    }

    public String getComment_MainId() {
        return comment_MainId;
    }

    public void setComment_MainId(String comment_MainId) {
        this.comment_MainId = comment_MainId;
    }

    public String getComment_UID() {
        return comment_UID;
    }

    public void setComment_UID(String comment_UID) {
        this.comment_UID = comment_UID;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_dateCommented() {
        return comment_dateCommented;
    }

    public void setComment_dateCommented(String comment_dateCommented) {
        this.comment_dateCommented = comment_dateCommented;
    }

    public String getComment_loclat() {
        return comment_loclat;
    }

    public void setComment_loclat(String comment_loclat) {
        this.comment_loclat = comment_loclat;
    }

    public String getComment_loclong() {
        return comment_loclong;
    }

    public void setComment_loclong(String comment_loclong) {
        this.comment_loclong = comment_loclong;
    }

    public String getComment_fetchat() {
        return comment_fetchat;
    }

    public void setComment_fetchat(String comment_fetchat) {
        this.comment_fetchat = comment_fetchat;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_mainid() {
        return content_mainid;
    }

    public void setContent_mainid(String content_mainid) {
        this.content_mainid = content_mainid;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent_description() {
        return content_description;
    }

    public void setContent_description(String content_description) {
        this.content_description = content_description;
    }

    public String getContent_imageUrl() {
        return content_imageUrl;
    }

    public void setContent_imageUrl(String content_imageUrl) {
        this.content_imageUrl = content_imageUrl;
    }

    public String getContent_event() {
        return content_event;
    }

    public void setContent_event(String content_event) {
        this.content_event = content_event;
    }

    public String getContent_fileURL() {
        return content_fileURL;
    }

    public void setContent_fileURL(String content_fileURL) {
        this.content_fileURL = content_fileURL;
    }

    public String getContent_fetchat() {
        return content_fetchat;
    }

    public void setContent_fetchat(String content_fetchat) {
        this.content_fetchat = content_fetchat;
    }

    public String getSubcomm_id() {
        return subcomm_id;
    }

    public void setSubcomm_id(String subcomm_id) {
        this.subcomm_id = subcomm_id;
    }

    public String getSubcomm_commenid() {
        return subcomm_commenid;
    }

    public void setSubcomm_commentId(String subcomm_commenid) {
        this.subcomm_commenid = subcomm_commenid;
    }

    public String getSubcomm_uid() {
        return subcomm_uid;
    }

    public void setSubcomm_uid(String subcomm_uid) {
        this.subcomm_uid = subcomm_uid;
    }

    public String getSubcomm_content() {
        return subcomm_content;
    }

    public void setSubcomm_content(String subcomm_content) {
        this.subcomm_content = subcomm_content;
    }

    public String getSubcomm_dateCommented() {
        return subcomm_dateCommented;
    }

    public void setSubcomm_dateCommented(String subcomm_dateCommented) {
        this.subcomm_dateCommented = subcomm_dateCommented;
    }

    public String getSubcomm_loclat() {
        return subcomm_loclat;
    }

    public void setSubcomm_loclat(String subcomm_loclat) {
        this.subcomm_loclat = subcomm_loclat;
    }

    public String getSubcomm_loclong() {
        return subcomm_loclong;
    }

    public void setSubcomm_loclong(String subcomm_loclong) {
        this.subcomm_loclong = subcomm_loclong;
    }

    public String getSubcomm_fetchat() {
        return subcomm_fetchat;
    }

    public void setSubcomm_fetchat(String subcomm_fetchat) {
        this.subcomm_fetchat = subcomm_fetchat;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getMain_mainuid() {
        return main_mainuid;
    }

    public void setMain_mainuid(String main_mainuid) {
        this.main_mainuid = main_mainuid;
    }

    public String getMain_date() {
        return main_date;
    }

    public void setMain_date(String main_date) {
        this.main_date = main_date;
    }

    public String getMain_loclat() {
        return main_loclat;
    }

    public void setMain_loclat(String main_loclat) {
        this.main_loclat = main_loclat;
    }

    public String getMain_loclong() {
        return main_loclong;
    }

    public void setMain_loclong(String main_loclong) {
        this.main_loclong = main_loclong;
    }

    public String getMain_fetchat() {
        return main_fetchat;
    }

    public void setMain_fetchat(String main_fetchat) {
        this.main_fetchat = main_fetchat;
    }

    public String getMain_seenState() {
        return main_seenState;
    }

    public void setMain_seenState(String main_seenState) {
        this.main_seenState = main_seenState;
    }

    public String getMain_Address() {
        return main_Address;
    }

    public void setMain_Address(String main_Address) {
        this.main_Address = main_Address;
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public int getIsAquaActive() {
        return isAquaActive;
    }

    public void setIsAquaActive(int isAquaActive) {
        this.isAquaActive = isAquaActive;
    }

    public int getIsPetOneActive() {
        return isPetOneActive;
    }

    public void setIsPetOneActive(int isPetOneActive) {
        this.isPetOneActive = isPetOneActive;
    }

    public int getIsHogsActive() {
        return isHogsActive;
    }

    public void setIsHogsActive(int isHogsActive) {
        this.isHogsActive = isHogsActive;
    }
}
