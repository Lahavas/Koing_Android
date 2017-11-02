package com.tourwith.koing.ViewPager;

import android.content.Context;

import com.tourwith.koing.R;

import java.util.HashMap;

/**
 * Created by Munak on 2017. 11. 2..
 */


/*
    <string-array name="language">
        <item >English</item>   EN
        <item >Korean</item>    KO
        <item >Japanese</item>  JA
        <item >Chinese</item>   ZH
        <item >Spanish</item>   ES
        <item >French</item>    FR
    </string-array>
 */

public class LanguageToShort {

    Context context;
    String[] langs;
    HashMap<String, String> langToShortMap;

    public LanguageToShort(Context context) {

        this.context = context;

        langs = new String[7];

        langs = context.getResources().getStringArray(R.array.language);

        langToShortMap = new HashMap();

        langToShortMap.put(langs[0],"EN");
        langToShortMap.put(langs[1],"KO");
        langToShortMap.put(langs[2],"JA");
        langToShortMap.put(langs[3],"ZH");
        langToShortMap.put(langs[4],"ES");
        langToShortMap.put(langs[5],"FR");

    }

    public String ConverteToShort(String lang) {
        return langToShortMap.get(lang);
    }


}
