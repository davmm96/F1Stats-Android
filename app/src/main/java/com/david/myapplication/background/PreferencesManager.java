package com.david.myapplication.background;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String SHARED_PREFS_FILE = "PrefsManager";
    private static final String MUSIC_STATE = "MUSIC_STATE";

    private Context mContext;

    public PreferencesManager(Context context){
        mContext = context;
    }

    private SharedPreferences getPreferences(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public boolean isMusicEnabled(){
        return getPreferences().getBoolean(MUSIC_STATE, false);
    }

    public void setMusicEnabled(boolean state){
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putBoolean(MUSIC_STATE, state);
        edit.apply();
    }
}
