package com.droidhelios.clipboard.util;

import android.content.SharedPreferences;

import com.droidhelios.clipboard.AppApplication;

public class SharedPrefUtil {

    private static final String ENABLE_CLIPBOARD = "enable_clipboard";
    private static final String ENABLE_AUTO_COPY = "enable_auto_copy";

    public static String getString(String key) {
        return getDefaultSharedPref().getString(key, "");
    }

    public static void setString(String key, String value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getDefaultSharedPref().getBoolean(key, defaultValue);
    }


    public static void setBoolean(String key, boolean value) {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void clearPreferences() {
        final SharedPreferences.Editor editor = getDefaultSharedPref().edit();
        editor.clear();
        editor.apply();
    }

    private static SharedPreferences getDefaultSharedPref() {
        return AppApplication.getInstance().getDefaultSharedPref();
    }

    public static boolean isEnableClipboard() {
        return getBoolean(ENABLE_CLIPBOARD, true);
    }

    public static void setEnableClipboard(boolean value) {
        setBoolean(ENABLE_CLIPBOARD, value);
    }

    public static boolean isEnableAutoCopy() {
        return getBoolean(ENABLE_AUTO_COPY, true);
    }

    public static void setEnableAutoCopy(boolean value) {
        setBoolean(ENABLE_AUTO_COPY, value);
    }

}