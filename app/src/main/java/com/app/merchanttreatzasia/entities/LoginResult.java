
package com.app.merchanttreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("is_verified")
    @Expose
    private int isVerified;
    @SerializedName("role_id")
    @Expose
    private int roleId;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("ma_service")
    @Expose
    private String maService;
    @SerializedName("in_service")
    @Expose
    private String inService;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("push_notification")
    @Expose
    private int pushNotification;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("socialmedia_type")
    @Expose
    private String socialmediaType;
    @SerializedName("socialmedia_id")
    @Expose
    private String socialmediaId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("earned_credit")
    @Expose
    private String earnedCredit;
    @SerializedName("used_credit")
    @Expose
    private String usedCredit;
    @SerializedName("is_deleted")
    @Expose
    private int isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("_token")
    @Expose
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMaService() {
        return maService;
    }

    public void setMaService(String maService) {
        this.maService = maService;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(int pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSocialmediaType() {
        return socialmediaType;
    }

    public void setSocialmediaType(String socialmediaType) {
        this.socialmediaType = socialmediaType;
    }

    public String getSocialmediaId() {
        return socialmediaId;
    }

    public void setSocialmediaId(String socialmediaId) {
        this.socialmediaId = socialmediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEarnedCredit() {
        return earnedCredit;
    }

    public void setEarnedCredit(String earnedCredit) {
        this.earnedCredit = earnedCredit;
    }

    public String getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(String usedCredit) {
        this.usedCredit = usedCredit;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
