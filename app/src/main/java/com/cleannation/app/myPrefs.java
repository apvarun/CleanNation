package com.cleannation.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by varun on 16/12/15.
 */
public class myPrefs {
    private static final String USER_PREFS = "USER_PREF";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private boolean key =false;

    public myPrefs(Context context){
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    public boolean getStat() {
        return appSharedPrefs.getBoolean("key", false);
    }

    public void setStat(boolean _pin) {
        key=_pin;
        prefsEditor.putBoolean("key",_pin).commit();
    }
}
