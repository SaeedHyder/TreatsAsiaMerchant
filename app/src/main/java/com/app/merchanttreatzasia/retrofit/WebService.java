package com.app.merchanttreatzasia.retrofit;


import com.app.merchanttreatzasia.entities.AboutUsEnt;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.entities.CurrencyRateEntity;
import com.app.merchanttreatzasia.entities.EVocherDetailEnt;
import com.app.merchanttreatzasia.entities.GoogleGeoCodeResponse;
import com.app.merchanttreatzasia.entities.GoogleServiceResponse;
import com.app.merchanttreatzasia.entities.MerchantEnt;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.entities.NotificationGetEnt;
import com.app.merchanttreatzasia.entities.ProfileEnt;
import com.app.merchanttreatzasia.entities.ResponseWrapper;
import com.app.merchanttreatzasia.entities.SubBranchHistoryEnt;
import com.app.merchanttreatzasia.entities.countEnt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {

    @FormUrlEncoded
    @POST("merchant/login")
    Call<ResponseWrapper<MerchantEnt>> loginMerchant(@Field("email") String email,
                                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("merchant/forgotpassword")
    Call<ResponseWrapper> forgotpassword(@Field("email") String email);

    @GET("merchant/getMerchantProfile")
    Call<ResponseWrapper<ProfileEnt>> getMerchantProfile(@Header("token") String token);

    @FormUrlEncoded
    @POST("merchant/MerchantUpdateProfile")
    Call<ResponseWrapper<ProfileEnt>> merchantUpdateProfile(@Field("first_name") String first_name,
                                                            @Field("location") String location,
                                                            @Field("latitude") String latitude,
                                                            @Field("longitude") String longitude,
                                                            @Header("token") String token);

    @GET("getMerchantRedeemHistory")
    Call<ResponseWrapper<List<MerchantRedeemHistoryEnt>>> getMerchantRedeemHistory(
            @Header("token") String token
    );

    @GET("getMerchantRedeemHistory")
    Call<ResponseWrapper<List<MerchantRedeemHistoryEnt>>> getMerchantRedeemHistorySort(
            @Query("sort") String sort,/*
            @Query("search") String search,*/
            @Header("token") String token
    );

    @GET("getMerchantRedeemDetail")
    Call<ResponseWrapper<MerchantRedeemHistoryEnt>> getMerchantRedeemDetail(
            @Query("evoucher_id") String evoucher_id,
            @Header("token") String token);


    @GET("cms/getCms")
    Call<ResponseWrapper<AboutUsEnt>> aboutUs(
            @Query("type") String type,
            @Header("token") String token);


    @FormUrlEncoded
    @POST("user/updateDeviceToken")
    Call<ResponseWrapper> updateToken(
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Header("token") String token);

    @GET("/maps/api/geocode/json")
    Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> getLatLongInfo(

            @Query("address") String address,
            @Query("sensor") String sensor);

    @GET("HistoryList")
    Call<ResponseWrapper<List<BranchEnt>>> getHistoryList(
            @Header("token") String token
    );

    @GET("HistoryList")
    Call<ResponseWrapper<List<BranchEnt>>> getHistoryListSort(
            @Query("sort") String sort,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("merchantbranch")
    Call<ResponseWrapper> createMerchantBranch(@Field("first_name") String first_name,
                                               @Field("email") String email,
                                               @Field("password") String password,
                                               @Field("password_confirmation") String password_confirmation,
                                               @Header("token") String token);

    @GET("getBranchList")
    Call<ResponseWrapper<List<BranchEnt>>> getBranchList(
            @Header("token") String token);

    @GET("getBranchList")
    Call<ResponseWrapper<List<BranchEnt>>> getBranchListSort(
            @Query("sort") String sort,
            @Header("token") String token);

    @GET("getBranchProfile")
    Call<ResponseWrapper<BranchEnt>> getBranchProfile(
            @Query("branch_id") String branch_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("branchdelete")
    Call<ResponseWrapper> deleteBranch(
            @Field("branch_id") String branch_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("updatebranchprofile")
    Call<ResponseWrapper> updateBranchProfile(
            @Field("branch_id") String branch_id,
            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Header("token") String token
    );

    @GET("branchHistory")
    Call<ResponseWrapper<SubBranchHistoryEnt>> getBranchHistory(
            @Query("branch_id") String branch_id,
            @Header("token") String token);

    @GET("branchHistory")
    Call<ResponseWrapper<SubBranchHistoryEnt>> getBranchHistorySort(
            @Query("branch_id") String branch_id,
            @Query("sort") String sort,
            @Header("token") String token);

    @FormUrlEncoded
    @POST("changepushnotificationstatus")
    Call<ResponseWrapper> changepushnotificationstatus(
            @Field("push_notification") String push_notification,
            @Header("token") String token
    );

    @GET("getnotifications")
    Call<ResponseWrapper<List<NotificationGetEnt>>> getNotification(
            @Header("token") String token
    );

    @GET("getUnreadNotificationsCount")
    Call<ResponseWrapper<countEnt>> getNotificationCount(
            @Header("token") String token
    );

    @GET("getMerchantEvoucherDetail")
    Call<ResponseWrapper<EVocherDetailEnt>> getMerchantEvoucherDetail(
            @Query("code") String code,
            @Header("token") String token
    );


    @FormUrlEncoded
    @POST("MerchantPasswardCheck")
    Call<ResponseWrapper> merchantPasswardCheck(@Field("merchant_id") String merchant_id,
                                                @Field("password") String password);

    @FormUrlEncoded
    @POST("MerchantVerification")
    Call<ResponseWrapper> merchantVerification(@Field("merchant_id_code") String merchant_id_code,
                                               @Header("token") String token);

    @FormUrlEncoded
    @POST("CouponRedeem")
    Call<ResponseWrapper> couponRedeem(
            @Field("evoucher_id") String evoucher_id,
            @Field("user_id") String user_id,
            @Field("qr_code") String qr_code,
            @Field("remaining_amount") String amount,
            @Header("token") String token
    );

    @GET("user/logoutandroid")
    Call<ResponseWrapper> logout(
            @Header("token") String token
    );

    @GET("currencyRate")
    Call<ResponseWrapper<CurrencyRateEntity>> getCurrencyRate(
            @Query("from") String from,
            @Query("to") String to
    );
}