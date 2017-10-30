package com.tourwith.koing.ViewPager;

import android.content.Context;

import com.tourwith.koing.R;

import java.util.HashMap;

/**
 * Created by Munak on 2017. 10. 30..
 */

/*
    <string-array name="language">
        <item >English</item>
        <item >Korean</item>
        <item >Japanese</item>
        <item >Chinese</item>
        <item >Spanish</item>
        <item >French</item>
    </string-array>
 */


public class LanguageToFlag {

    Context context;
    String[] languages;
    HashMap<String, Integer> langToFlagMap;

    public LanguageToFlag(Context context) {
        this.context = context;

        languages = context.getResources().getStringArray(R.array.language);

        langToFlagMap = new HashMap();

        langToFlagMap.put(languages[0],R.drawable.us);
        langToFlagMap.put(languages[1],R.drawable.ko);
        langToFlagMap.put(languages[2],R.drawable.jp);
        langToFlagMap.put(languages[3],R.drawable.cn);
        langToFlagMap.put(languages[4],R.drawable.es);
        langToFlagMap.put(languages[5],R.drawable.fr);

    }

    public int Converter(String language) {
        return langToFlagMap.get(language);
    }


}
