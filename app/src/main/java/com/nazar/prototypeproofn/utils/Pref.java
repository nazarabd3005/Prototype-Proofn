package com.nazar.prototypeproofn.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nazar.prototypeproofn.Application;

public class Pref {
    private static SharedPreferences preferences;

    public static void init() {
        if (Pref.preferences == null) {
            Pref.preferences = Application.getInstance().getSharedPreferences("com.nazar", Context.MODE_PRIVATE);
        }
    }

    static SharedPreferences getPreferences() {
        Pref.init();

        return Pref.preferences;
    }

    static SharedPreferences.Editor getEditor() {
        Pref.init();

        return Pref.preferences == null ? null : Pref.preferences.edit();
    }

    static void clearAll() {
        SharedPreferences.Editor editor = Pref.getEditor();
        if (editor != null) {
            editor.clear();
            editor.apply();
        }
    }

    public static String getString(String keyName, String defaultValue) {
        Pref.init();

        if (Pref.preferences != null) {
            return Pref.preferences.getString(keyName, defaultValue);
        }

        return defaultValue;
    }

    public static String getString(String keyName) {
        return Pref.getString(keyName, "");
    }

    public static void setString(String keyName, String value) {
        Pref.init();

        SharedPreferences.Editor editor = Pref.getEditor();
        if (editor != null) {
            editor.putString(keyName, value);
            editor.apply();
        }
    }

    public static void delString(String keyName) {
        Pref.init();

        SharedPreferences.Editor editor = Pref.getEditor();
        if (editor != null) {
            editor.remove(keyName);
            editor.apply();
        }
    }
}
