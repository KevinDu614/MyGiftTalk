package com.l000phone.mygifttalk.utils;

import android.content.Context;

public class PreUtils {
    private static final String PREF_NAME = "config";

    //获得是否开启过引导页
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        boolean userGUide = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
        return userGUide;
    }

    //设置开启过引导页
    public static void setBoolean(Context context, String key, boolean defaultValue) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, defaultValue).commit();
    }

    //保存用户性别
    public static void setUserGender(Context context, String key, String data) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(key, data).commit();
    }

    //保存用户身份
    public static void setUserIdentity(Context context, String key, String data) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(key, data).commit();
    }


    //获得用户的性别和身份
    public static boolean getBooleanUserInfo(Context context, String key, boolean defaultValue) {
        boolean userGender = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean("userGenderInfo", defaultValue);
        return userGender;
    }

    //存储用户性别和身份
    public static void setBooleanUserInfo(Context context, String key, boolean defaultValue) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, defaultValue).commit();
    }
}
