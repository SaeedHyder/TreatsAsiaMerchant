
package com.app.merchanttreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantRedeemHistoryEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("evoucher_id")
    @Expose
    private Integer evoucherId;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("qr_code_url")
    @Expose
    private String qrCodeUrl;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("gift_status")
    @Expose
    private String giftStatus;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_currency")
    @Expose
    private String transactionCurrency;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("credit_amount")
    @Expose
    private String creditAmount;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("evoucher_detail")
    @Expose
    private EvoucherDetail evoucherDetail;

    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;

    @SerializedName("merchant_detail")
    @Expose
    private MerchantDetail merchantDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEvoucherId() {
        return evoucherId;
    }

    public void setEvoucherId(Integer evoucherId) {
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public EvoucherDetail getEvoucherDetail() {
        return evoucherDetail;
    }

    public void setEvoucherDetail(EvoucherDetail evoucherDetail) {
        this.evoucherDetail = evoucherDetail;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public MerchantDetail getMerchantDetail() {
        return merchantDetail;
    }

    public void setMerchantDetail(MerchantDetail merchantDetail) {
        this.merchantDetail = merchantDetail;
    }

    /* @SerializedName("id")
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

    @SerializedName("point")
    @Expose
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

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
*/
}
