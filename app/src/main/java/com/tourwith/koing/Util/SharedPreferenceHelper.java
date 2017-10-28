package com.tourwith.koing.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hanhb on 2017-10-01.
 */

public class SharedPreferenceHelper {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public final String AUTO_LOGIN = "autoLogin";
    /*
        key list
        
     */

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        this.context = context;
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value){
        editor.putBoolean(key, value);
        editor.commit();
    }

}
