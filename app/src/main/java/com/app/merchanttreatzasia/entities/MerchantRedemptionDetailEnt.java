
package com.app.merchanttreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantRedemptionDetailEnt {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("evoucher_id")
    @Expose
    private int evoucherId;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("qr_code_url")
    @Expose
    private String qrCodeUrl;
    @SerializedName("merchant_id")
    @Expose
    private int merchantId;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("evoucher_detail")
    @Expose
    private EvoucherDetail evoucherDetail;
    @SerializedName("merchant_detail")
    @Expose
    private MerchantDetail merchantDetail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEvoucherId() {
        return evoucherId;
    }

    public void setEvoucherId(int evoucherId) {
        this.evoucherId = evoucherId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
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

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public EvoucherDetail getEvoucherDetail() {
        return evoucherDetail;
    }

    public void setEvoucherDetail(EvoucherDetail evoucherDetail) {
        this.evoucherDetail = evoucherDetail;
    }

    public MerchantDetail getMerchantDetail() {
        return merchantDetail;
    }

    public void setMerchantDetail(MerchantDetail merchantDetail) {
        this.merchantDetail = merchantDetail;
    }

}
