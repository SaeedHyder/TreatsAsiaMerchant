package com.app.merchanttreatzasia.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.merchanttreatzasia.entities.MerchantEnt;
import com.app.merchanttreatzasia.retrofit.GsonFactory;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String Firebase_TOKEN = "Firebasetoken";
    protected static final String USERID = "userId";
    protected static final String KEY_USER = "key_user";
    private static final String FILENAME = "preferences";
    protected static final String LANGUAGE = "Language";
    protected static final String NotificationCount = "NotificationCount";

    protected static final String SORT = "sort";
    protected static final String NotificationToggle = "notificationToggle";
    protected static final String SEARCH = "search";

    protected static final String BRANCH_SORT = "Branch_sort";
    protected static final String BRANCH_DATA_SORT = "Branch_data_sort";
    protected static final String ConvertedAmount = "ConvertedAmount";
    protected static final String convertedAmountCurrency = "convertedAmountCurrency";

    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }
    public int getNotificationCount() {
        return getIntegerPreference(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }
    public void setmerchantId(String userId) {
        putStringPreference(context, FILENAME, USERID, userId);
    }

    public String getMerchantId() {
        return getStringPreference(context, FILENAME, USERID);
    }

    public MerchantEnt getMerchant() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), MerchantEnt.class);
    }

    public void putMerchant(MerchantEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory.getConfiguredGson().toJson(user));
    }

    public void setLanguage(String lanugage) {
        putStringPreference(context, FILENAME, LANGUAGE, lanugage);
    }

    public String getLanguage() {
        return getStringPreference(context, FILENAME, LANGUAGE);
    }

    public void setSort(String sort) {
        putStringPreference(context, FILENAME, SORT, sort);
    }

    public String getSort() {
        return getStringPreference(context, FILENAME, SORT);
    }

    public void setNotifiationSatus(String notificationToggle) {
        putStringPreference(context, FILENAME, NotificationToggle, notificationToggle);
    }

    public String getNotifiationSatus() {
        return getStringPreference(context, FILENAME, NotificationToggle);
    }

    public void setSearch(String search) {
        putStringPreference(context, FILENAME, SEARCH, search);
    }

    public String getSearch() {
        return getStringPreference(context, FILENAME, SEARCH);
    }

    public void setBranchSort(String sort) {
        putStringPreference(context, FILENAME, BRANCH_SORT, sort);
    }

    public String getBranchSort() {
        return getStringPreference(context, FILENAME, BRANCH_SORT);
    }

    public void setBranchDataSort(String sort) {
        putStringPreference(context, FILENAME, BRANCH_DATA_SORT, sort);
    }

    public String getBranchDataSort() {
        return getStringPreference(context, FILENAME, BRANCH_DATA_SORT);
    }

    public Float getConvertedAmount() {
        return getFloatPreference(context, FILENAME, ConvertedAmount);
    }

    public void setConvertedAmount(Float convertedAmount) {
        putFloatPreference(context, FILENAME, ConvertedAmount, convertedAmount);
    }

    public String getConvertedAmountCurrrency() {
        return getStringPreference(context, FILENAME, convertedAmountCurrency);
    }

    public void setConvertedAmountCurrency(String convertedAmountCurrencys) {
        putStringPreference(context, FILENAME, convertedAmountCurrency, convertedAmountCurrencys);
    }

}
